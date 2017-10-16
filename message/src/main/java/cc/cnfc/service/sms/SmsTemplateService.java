/**
 * 
 */
package cc.cnfc.service.sms;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.cnfc.core.orm.Page;
import cc.cnfc.core.orm.hibernate.HibernatePage;
import cc.cnfc.core.service.BaseService;
import cc.cnfc.dao.SmsTemplateDao;
import cc.cnfc.entity.SmsTemplate;
import cc.cnfc.pub.constant.Const;
import cc.cnfc.pub.constant.Const.SmsTemplateStatus;
import cc.cnfc.service.CustomerService;

/**
 * @author luoyb
 *
 */
@Service
@Transactional
public class SmsTemplateService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8187653412213113317L;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustomerService customerInfoSvc;
	@Autowired
	private SmsTemplateDao smsTemplateDao;

	/**
	 * 获取某个系统所有的短信模版
	 * 
	 * @param mSysId
	 * @return
	 */
	public List<SmsTemplate> getAll(String mSysId) {
		return smsTemplateDao.find("from SmsTemplate where mSysId = ? and latest = ? ", mSysId, true);
	}

	public Page list(String mSysId) {
		HibernatePage hibernatePage = new HibernatePage();
		hibernatePage.setQueryClass(SmsTemplate.class);
		hibernatePage.setConditionEqual("mSysId", mSysId);
		// hibernatePage.setConditionEqual("status", SmsTemplateStatus.PASS.toString());
		hibernatePage.setConditionEqual("latest", true);
		hibernatePage.setOrderBy("createTime desc");
		Page page = this.query4JqGrid(hibernatePage);
		return page;
	}

	public Page waitVerifyList() {
		HibernatePage hibernatePage = new HibernatePage();
		hibernatePage.setQueryClass(SmsTemplate.class);
		hibernatePage.setConditionEqual("status", SmsTemplateStatus.APPLYING.toString());
		hibernatePage.setOrderBy("mSysId,createTime desc");
		Page page = this.query4JqGrid(hibernatePage);
		return page;
	}

	public void delete(String mSysId, String key) {
		List<SmsTemplate> templateLs = this.getTemplates(mSysId, key);
		for (SmsTemplate temp : templateLs) {
			this.delete(temp);
		}
	}

	/**
	 * 新增或编辑短信模版
	 * 
	 * @param mSysId
	 * @param key
	 *            模版key
	 * @param templateCnt
	 *            模版内容
	 * @throws Exception
	 */
	public void saveTemplate(String mSysId, String key, String templateName, String templateCnt, String status)
			throws Exception {
		if (StringUtils.isBlank(key)) {
			throw new Exception("模板key不能为空！");
		}
		SmsTemplate template = this.getLatestTemplate(mSysId, key);
		// 审核中允许修改
		if (template != null && SmsTemplateStatus.APPLYING.toString().equals(template.getStatus())) {
			// throw new RuntimeException("该模板还在审核中，操作失败！");
			template.setTemplateName(templateName);
			template.setTemplateCnt(templateCnt);
			template.setUpdateTime(new Date());
			smsTemplateDao.save(template);
			return;
		}
		// 新增一条审核记录
		if (template != null) {
			template.setLatest(false);
			this.update(template);
		}
		SmsTemplate newTemp = new SmsTemplate();
		newTemp.setmSysId(mSysId);
		newTemp.setTemplateKey(key);
		newTemp.setTemplateName(templateName);
		newTemp.setTemplateCnt(templateCnt);
		newTemp.setStatus(status);
		newTemp.setCreateTime(new Date());
		newTemp.setUpdateTime(new Date());
		newTemp.setLatest(true);
		smsTemplateDao.save(newTemp);
	}

	/**
	 * 审核模板
	 * 
	 * @param mSysId
	 * @param key
	 * @param templateName
	 * @param templateCnt
	 * @param status
	 * @throws Exception
	 */
	public void verify(String mSysId, String key, String status) throws Exception {
		if (StringUtils.isBlank(key)) {
			throw new Exception("模板key不能为空！");
		}
		SmsTemplate template = this.getLatestTemplate(mSysId, key);
		if (template == null || !SmsTemplateStatus.APPLYING.toString().equals(template.getStatus())) {
			throw new RuntimeException("该模板不处在还在审核中，操作失败！");
		}

		SmsTemplate histTemp = this.getLatestTemplate(mSysId, key, status);
		if (histTemp != null) {
			smsTemplateDao.delete(histTemp);
		}

		template.setStatus(status);
		template.setUpdateTime(new Date());
		smsTemplateDao.save(template);
	}

	public List<SmsTemplate> getTemplates(String mSysId, String key) {
		List<SmsTemplate> templateLs = smsTemplateDao.find("from SmsTemplate where mSysId = ? and templateKey = ?",
				mSysId, key);
		return templateLs;
	}

	/**
	 * 获取获取最近的一条短信模版
	 * 
	 * @param mSysId
	 * @param key
	 * @return
	 */
	public SmsTemplate getLatestTemplate(String mSysId, String key) {
		SmsTemplate template = (SmsTemplate) smsTemplateDao
				.createQuery("from SmsTemplate where mSysId = ? and templateKey = ? order by id desc", mSysId, key)
				.setMaxResults(1).uniqueResult();
		return template;
	}

	public SmsTemplate getLatestTemplate(String mSysId, String key, String status) {
		SmsTemplate template = (SmsTemplate) smsTemplateDao
				.createQuery("from SmsTemplate where mSysId = ? and templateKey = ? and status = ? order by id desc",
						mSysId, key, status).setMaxResults(1).uniqueResult();
		return template;
	}

	/**
	 * 用数据填充模板
	 * 
	 * @param mSysId
	 * @param templateKey
	 * @param datas
	 * @return
	 * @throws Exception
	 */
	public String paddingSmsTemplate(String mSysId, String templateKey, Object... datas) throws Exception {
		SmsTemplate template = this.getLatestTemplate(mSysId, templateKey, SmsTemplateStatus.PASS.toString());
		if (template == null) {
			throw new Exception("没找到对应的模板！");
		}
		StringBuffer sb = new StringBuffer();
		try {
			String templateCnt = template.getTemplateCnt();
			Pattern p = Pattern.compile(Const.SMS_PLACEHOLDER);
			Matcher m = p.matcher(templateCnt);
			int index = 0;
			while (m.find()) {
				m.appendReplacement(sb, datas[index].toString());
				index++;
			}
			m.appendTail(sb);
		} catch (Exception e) {
			logger.error("paddingSmsTemplate error...", e);
			throw new Exception("数据填充短信模板出错，请检查模板和数据是否匹配！");
		}
		return sb.toString();
	}

	public String paddingSmsTemplate(String mSysId, String templateKey, Map<String, ?> dataMap) throws Exception {
		SmsTemplate template = this.getLatestTemplate(mSysId, templateKey, SmsTemplateStatus.PASS.toString());
		if (template == null) {
			throw new Exception("没找到对应的模板！");
		}
		StringBuffer sb = new StringBuffer();
		try {
			String templateCnt = template.getTemplateCnt();
			String regex = "\\#\\$\\{(" + StringUtils.join(dataMap.keySet(), "|") + ")\\}";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(templateCnt);
			while (matcher.find()) {
				matcher.appendReplacement(sb, dataMap.get(matcher.group(1)) + "");
			}
			matcher.appendTail(sb);
		} catch (Exception e) {
			logger.error("paddingSmsTemplate error...", e);
			throw new Exception("数据填充短信模板出错，请检查模板和数据是否匹配！");
		}
		return sb.toString();
	}

}
