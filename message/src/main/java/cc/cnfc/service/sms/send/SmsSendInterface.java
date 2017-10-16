package cc.cnfc.service.sms.send;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cc.cnfc.dao.SmsDao;
import cc.cnfc.entity.SmsLog;

/*
 * 各种短信实现的抽象类
 * 
 * 发送链：
 * biz，nexmo -> yc -> chonry
 * marketing，nexmoMarketing -> ycMarketing -> chonry
 * 
 */
public abstract class SmsSendInterface {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SmsDao smsDao;

	/**
	 * 发送短信的外部调用方法
	 * 
	 * @param mSysId
	 * @param smsPrefix
	 * @param mobile
	 * @param content
	 * @throws Exception
	 */
	public void send(String mSysId, String smsPrefix, String mobile, String content) {
		boolean sendStatus = this.sendSms(mSysId, smsPrefix, mobile, content);
		if (!sendStatus) {
			this.nextSendSms(mSysId, smsPrefix, mobile, content);
		}
	}

	/**
	 * 发送短信
	 * 
	 * @param mSysId
	 * @param smsPrefix
	 * @param mobile
	 * @param content
	 * @throws Exception
	 */
	protected abstract boolean sendSms(String mSysId, String smsPrefix, String mobile, String content);

	/**
	 * 如果短信发送失败，则转到下一个短信接口去发送
	 */
	protected abstract void nextSendSms(String mSysId, String smsPrefix, String mobile, String content);

	/**
	 * 短信发送日志
	 * 
	 * @param mSysId
	 * @param mobile
	 * @param content
	 * @param msgId
	 * @param status
	 * @param statusReason
	 * @param sendChannel
	 */
	protected void saveSmsLog(String mSysId, String mobile, String content, String msgId, String status,
			String statusReason, String sendChannel) {
		logger.info("sms send info，mobile：" + mobile + "，content：" + content + "，status：" + status + "，statusReason："
				+ statusReason + "，messageid：" + msgId + "，mSysId：" + mSysId + "，sendChannel：" + sendChannel);
		SmsLog smsLog = new SmsLog();
		smsLog.setMobile(mobile);
		smsLog.setContent(content);
		smsLog.setMsgId(msgId);
		smsLog.setSendTime(new Date());
		smsLog.setStatus(status);
		smsLog.setStatusReason(statusReason);
		smsLog.setmSysId(mSysId);
		smsLog.setSendChannel(sendChannel);
		smsDao.save(smsLog);
	}

}
