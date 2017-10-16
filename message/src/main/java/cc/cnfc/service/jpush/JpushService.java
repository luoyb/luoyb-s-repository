/**
 * 
 */
package cc.cnfc.service.jpush;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.cnfc.dao.JpushDao;
import cc.cnfc.dto.jpush.JpushDto;
import cc.cnfc.entity.JpushLog;
import cc.cnfc.entity.ac.Customer;
import cc.cnfc.pub.constant.Const;
import cc.cnfc.service.CustomerService;
import cc.cnfc.service.MqService;
import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.WinphoneNotification;

/**
 * @author luoyb
 *
 */
@Service
@Transactional
public class JpushService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MqService mqSvc;
	@Autowired
	private CustomerService customerSvc;
	@Autowired
	private JpushDao jpushDao;

	protected final Object createJpushClientLock = new Object();

	/**
	 * mSysId->jpushClient的map
	 */
	private final ConcurrentHashMap<String, JPushClient> appId2JpushClient = new ConcurrentHashMap<String, JPushClient>();

	private JPushClient getJpushClient(String mSysId) {
		if (!appId2JpushClient.containsKey(mSysId)) {
			synchronized (createJpushClientLock) {
				if (!appId2JpushClient.containsKey(mSysId)) {
					Customer ci = customerSvc.findByMSysId(mSysId);
					JPushClient jpushClient = new JPushClient(ci.getJpushMasterSecret(), ci.getJpushAppKey());
					appId2JpushClient.put(mSysId, jpushClient);
				}
			}
		}
		return appId2JpushClient.get(mSysId);
	}

	/**
	 * 处理接收到的jpush数据
	 * 
	 * For push, all you need do is to build PushPayload object.
	 */
	public void handlerJpush(String mSysId, JpushDto jDto) {
		// logger.info(payload.toJSON().toString());
		PushPayload payload = getPushPayload(jDto);
		try {
			JPushClient jpushClient = this.getJpushClient(mSysId);
			PushResult result = jpushClient.sendPush(payload);
			logger.info("Got jpush result - " + result);
			this.saveJpushLog(mSysId, payload.toJSON().toString(), result.toString(), Const.YES, StringUtils.EMPTY);
		} catch (APIConnectionException e) {
			// Connection error, should retry later
			logger.error("Connection error, should retry later", e);
			this.saveJpushLog(mSysId, payload.toJSON().toString(), e.getMessage(), Const.NO, e.getMessage());
		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			logger.error("Should review the error, and fix the request", e);
			logger.info("HTTP Status: " + e.getStatus());
			logger.info("Error Code: " + e.getErrorCode());
			logger.info("Error Message: " + e.getErrorMessage());
			this.saveJpushLog(mSysId, payload.toJSON().toString(), e.getMessage(), Const.NO, e.getMessage());
		}
	}

	private void saveJpushLog(String mSysId, String pushMsg, String respondMsg, String status, String statusReason) {
		JpushLog log = new JpushLog();
		log.setmSysId(mSysId);
		log.setPushMsg(pushMsg);
		log.setRespondMsg(respondMsg);
		log.setSendTime(new Date());
		log.setStatus(status);
		log.setStatusReason(statusReason);
		jpushDao.save(log);
	}

	private PushPayload getPushPayload(JpushDto jDto) {
		switch (jDto.getJpushAudienceType()) {
		case ALL:
			return PushPayload.alertAll(jDto.getAlert());
		case ALIAS:
			return PushPayload
					.newBuilder()
					.setPlatform(Platform.all())
					.setAudience(Audience.alias(jDto.getAlias()))
					.setNotification(
							Notification
									.newBuilder()
									.addPlatformNotification(
											AndroidNotification.newBuilder().setAlert(jDto.getAlert())
													.setTitle(jDto.getTitle()).addExtras(jDto.getExtras()).build())
									.addPlatformNotification(
											IosNotification.newBuilder().setAlert(jDto.getAlert())
													.addExtras(jDto.getExtras()).build())
									.addPlatformNotification(
											WinphoneNotification.newBuilder().setAlert(jDto.getAlert())
													.addExtras(jDto.getExtras()).build()).build())
					.setMessage(Message.newBuilder().setMsgContent(jDto.getAlert()).addExtras(jDto.getExtras()).build())
					.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
		default:
			return null;
		}

	}
}
