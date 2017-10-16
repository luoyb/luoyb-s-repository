/**
 * 
 */
package cc.cnfc.service.sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import cc.cnfc.core.orm.Page;
import cc.cnfc.core.orm.hibernate.HibernatePage;
import cc.cnfc.core.service.BaseService;
import cc.cnfc.dao.TempSmsDao;
import cc.cnfc.dto.MemberInfo;
import cc.cnfc.dto.MemberInfoResult;
import cc.cnfc.dto.MqDto;
import cc.cnfc.dto.sms.SmsDto;
import cc.cnfc.entity.SmsLog;
import cc.cnfc.entity.TempSms;
import cc.cnfc.entity.ac.Customer;
import cc.cnfc.pub.constant.Const;
import cc.cnfc.pub.constant.Const.MqMsgType;
import cc.cnfc.pub.constant.Const.SmsSendChannel;
import cc.cnfc.service.CustomerService;
import cc.cnfc.service.MqService;
import cc.cnfc.service.sms.send.SmsMarketingSendByNexmo;
import cc.cnfc.service.sms.send.SmsMarketingSendByYc;
import cc.cnfc.service.sms.send.SmsSendByChonry;
import cc.cnfc.service.sms.send.SmsSendByYc;
import cc.cnfc.service.sms.send.SmsStrictSendByNexmo;
import cc.cnfc.util.DateUtil;
import cc.cnfc.util.Utils;

/**
 * @author luoyb
 *
 */
@Service
@Transactional
public class SmsService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8187653412213113317L;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MqService mqSvc;
	@Autowired
	private SmsMarketingSendByNexmo nexmoMarketingSms;
	@Autowired
	private SmsStrictSendByNexmo nexmoStrictSms;
	@Autowired
	private SmsSendByChonry chonrySms;
	@Autowired
	private SmsSendByYc ycSms;
	@Autowired
	private SmsMarketingSendByYc ycMarketingSms;
	@Autowired
	private CustomerService customerInfoSvc;
	@Autowired
	private TempSmsDao tempSmsDao;
	@Autowired
	private SmsBlacklistService smsBlacklistSvc;

	public Page findSmsLogByMSysId(String mSysId) {
		HibernatePage hibernatePage = new HibernatePage();
		hibernatePage.setQueryClass(SmsLog.class);
		hibernatePage.setConditionEqual("mSysId", mSysId);
		hibernatePage.setOrderBy("sendTime desc");
		Page page = this.query4JqGrid(hibernatePage);
		return page;
	}

	/**
	 * 把要发生的短信送入队列
	 */
	public void enqueueSms(String mSysId, SmsDto sDto) {
		SmsDto cleanedSDto = this.cleanSmsInfo(sDto);
		this.canSendSms(cleanedSDto);
		mqSvc.enqueue(new MqDto<SmsDto>(mSysId, MqMsgType.SMS, cleanedSDto));
	}

	/**
	 * 清理要发送的短信信息
	 */
	private SmsDto cleanSmsInfo(SmsDto sDto) {
		// 过滤重复的手机号，去除空格
		String[] mobiles = sDto.getMobile().split(",|，");
		Set<String> mobileSet = new HashSet<String>();
		for (String _mobile : mobiles) {
			String mobile = _mobile.trim();
			if (StringUtils.isNotBlank(mobile)) {
				mobileSet.add(mobile);
			}
		}
		String cleanedMobile = StringUtils.join(mobileSet.toArray(), ",");
		String cleanedCnt = sDto.getContent().trim();
		sDto.setMobile(cleanedMobile);
		sDto.setContent(cleanedCnt);
		return sDto;
	}

	/**
	 * 判断能否发送短信
	 * 
	 * @param sDto
	 */
	private void canSendSms(SmsDto sDto) {
		if (sDto == null) {
			throw new RuntimeException("发送对象为空");
		}
		if (Const.NO.equals(sDto.getIsGroupSend())) {
			if (StringUtils.isBlank(sDto.getMobile())) {
				throw new RuntimeException("手机号码为空");
			}
		}
		if (StringUtils.isBlank(sDto.getContent())) {
			throw new RuntimeException("短信内容为空");
		}
		if (Const.NO.equals(sDto.getIsGroupSend())) {
			String[] mobileArr = sDto.getMobile().split(",|，");
			for (String mobile : mobileArr) {
				if (!Utils.isMobile(mobile.trim())) {
					throw new RuntimeException(mobile + " 不是有效的手机号码");
				}
			}
		}
	}

	/**
	 * 当前时间是否符合发送短信的时间
	 * 
	 * @return
	 */
	public boolean time2SendSms() {
		if (DateUtil.getCurHour() > 6 && DateUtil.getCurHour() < 22) {
			return true;
		}
		return false;
	}

	public List<TempSms> getAllTempSms() {
		return tempSmsDao.getAll();
	}

	public void deleteTempSms(Long id) {
		tempSmsDao.delete(id);
	}

	/**
	 * 把多个手机号码组装成一个元素50个手机号码的数组，并且自动过滤重复的手机号码
	 * 
	 * @param result
	 * @return
	 */
	private List<String> assembleMobile(MemberInfoResult result) {
		List<MemberInfo> memberLs = result.getMemberArray();
		List<String> list = new ArrayList<String>();
		Set<String> set = new HashSet<String>();
		for (int index = 1; index < (memberLs.size() + 1); index++) {
			set.add(memberLs.get(index - 1).getMobile());
			if (index % Const.SMS_MOBILE_MAX_NUM == 0 || index == (memberLs.size())) {
				list.add(Utils.collectionToString(set));
				set = new HashSet<String>();
			}
		}
		return list;
	}

	/**
	 * 把多个手机号码组装成一个元素50个手机号码的数组，并且自动过滤重复的手机号码
	 * 
	 * @param mobiles
	 * @return
	 */
	public List<String> assembleMobile(String mobiles) {
		String[] mobileLArr = mobiles.split(",|，");
		List<String> list = new ArrayList<String>();
		Set<String> set = new HashSet<String>();
		for (int index = 1; index < (mobileLArr.length + 1); index++) {
			set.add(mobileLArr[index - 1]);
			if (index % Const.SMS_MOBILE_MAX_NUM == 0 || index == mobileLArr.length) {
				list.add(Utils.collectionToString(set));
				set = new HashSet<String>();
			}
		}
		return list;
	}

	/**
	 * 群发短信
	 * 
	 * @param customer
	 * @param sDto
	 * @param mSysId
	 */
	private void groupSend(Customer customer, SmsDto sDto, String mSysId) {
		String url = customer.getmGetMembersUrl();
		MemberInfoResult memberInfoResult = new RestTemplate().getForObject(url, MemberInfoResult.class);
		List<String> mobilesLs = this.assembleMobile(memberInfoResult);
		this._morethan1Send(customer, sDto, mSysId, mobilesLs);

	}

	/**
	 * 一次性提交超过1条的短信发送
	 */
	public void morethan1Send(Customer customer, SmsDto sDto, String mSysId) {
		List<String> mobilesLs = this.assembleMobile(sDto.getMobile());
		this._morethan1Send(customer, sDto, mSysId, mobilesLs);
	}

	/**
	 * 一次性提交超过1条的短信发送
	 */
	public void _morethan1Send(Customer customer, SmsDto sDto, String mSysId, List<String> mobilesLs) {
		String smsPrefix = StringUtils.isBlank(sDto.getSmsPrefix()) ? customer.getSmsPrefix() : sDto.getSmsPrefix();
		for (String mobiles : mobilesLs) {
			try {
				Thread.sleep(100);
				this.sendSms(mSysId, smsPrefix, sDto.getSendChannel(), mobiles, sDto.getContent());
			} catch (Exception e) {
				logger.error("group send sms error...", e);
			}
		}
	}

	public void singleSend(String mqDtoJson, String mSysId, SmsDto sDto, Customer customer) {
		String smsPrefix = StringUtils.isBlank(sDto.getSmsPrefix()) ? customer.getSmsPrefix() : sDto.getSmsPrefix();
		if (!sDto.isRealTimeSend() && !this.time2SendSms()) {
			TempSms tSms = new TempSms();
			tSms.setMqDtoJson(mqDtoJson);
			tSms.setCreateTime(new Date());
			tempSmsDao.save(tSms);
			logger.info("不是实时发送且不满足发送时间段的短信，先放入临时短信表");
			return;
		}
		try {
			String mobile = sDto.getMobile();
			Thread.sleep(1000);
			this.sendSms(mSysId, smsPrefix, sDto.getSendChannel(), mobile, sDto.getContent());
		} catch (Exception e) {
			logger.error("send sms error...", e);
		}
	}

	/**
	 * 处理接收到的短信
	 */
	public void handlerSms(String mqDtoJson, String mSysId, SmsDto sDto) {
		Customer customer = customerInfoSvc.findByMSysId(mSysId);
		// 群发
		if (Const.YES.equals(sDto.getIsGroupSend())) {
			this.groupSend(customer, sDto, mSysId);
			return;
		}
		// 一次性提交超过1条
		if (Const.YES.equals(sDto.isMoreThan1())) {
			this.morethan1Send(customer, sDto, mSysId);
			return;
		}
		// 单个发送
		this.singleSend(mqDtoJson, mSysId, sDto, customer);
	}

	private void sendSms(String mSysId, String smsPrefix, String sendChannel, String mobile, String content)
			throws Exception {

		mobile = smsBlacklistSvc.filterBlacklist(mSysId, mobile);
		if (StringUtils.isBlank(mobile)) {
			logger.info("after filter blacklist , mobile is empty , not send.");
			return;
		}

		Customer customer = customerInfoSvc.findByMSysId(mSysId);
		if (Const.YES.equals(customer.getSimulateSendSms())) {
			logger.info("模拟发送短信 , 不真发哦 , mSysId=" + mSysId + " , mobile=" + mobile + " , content=" + content);
			return;
		}

		if (SmsSendChannel.NEXMO_MARKETING.toString().equals(sendChannel)) {
			nexmoMarketingSms.send(mSysId, smsPrefix, mobile, content);
		} else if (SmsSendChannel.NEXMO_STRICT.toString().equals(sendChannel)) {
			nexmoStrictSms.send(mSysId, smsPrefix, mobile, content);
		} else if (SmsSendChannel.CHONRY.toString().equals(sendChannel)) {
			chonrySms.send(mSysId, smsPrefix, mobile, content);
		} else if (SmsSendChannel.YC.toString().equals(sendChannel)) {
			ycSms.send(mSysId, smsPrefix, mobile, content);
		} else if (SmsSendChannel.YC_MARKETING.toString().equals(sendChannel)) {
			ycMarketingSms.send(mSysId, smsPrefix, mobile, content);
		} else {
			logger.info("sendChannel is not setting , sms send by nexmo strict channel");
			nexmoStrictSms.send(mSysId, smsPrefix, mobile, content);
		}

	}
}
