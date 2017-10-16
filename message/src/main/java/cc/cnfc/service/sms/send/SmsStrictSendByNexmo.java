package cc.cnfc.service.sms.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import cc.cnfc.dto.sms.SmsResult;
import cc.cnfc.pub.constant.Const.SmsSendChannel;
import cc.cnfc.pub.constant.Const.SmsSendStatus;
import cc.cnfc.util.Utils;

/**
 * nexmo严格通道
 * 
 * @author luoyb
 *
 */
@Service
@Transactional
public class SmsStrictSendByNexmo extends SmsSendInterface {

	@Autowired
	private SmsSendByYc ycSms;

	@Override
	protected boolean sendSms(String mSysId, String smsPrefix, String mobile, String content) {
		try {
			String mobileWithPrefix = Utils.stringsAddPrefix(mobile, "86");

			String rawMessage = "【" + smsPrefix + "】" + content.trim();
			// String encodeMessage = URLEncoder.encode(rawMessage, "UTF-8");

			String url = "http://101.200.78.141/api/json?key=&secret=&to=" + mobileWithPrefix + "&text=" + rawMessage;
			SmsResult result = new RestTemplate().getForObject(url, SmsResult.class);

			String sendStatus = "0".equals(result.getStatus()) ? SmsSendStatus.DELIVER_SUCCESS.toString()
					: SmsSendStatus.DELIVER_FAIL.toString();
			this.saveSmsLog(mSysId, mobile, rawMessage, result.getMessageid(), sendStatus, result.getStatus_code(),
					SmsSendChannel.NEXMO_STRICT.toString());

			logger.info("sms send by nexmo strict channel");
			return SmsSendStatus.DELIVER_FAIL.toString().equals(sendStatus) ? false : true;
		} catch (Exception e) {
			logger.info("SmsStrictSendByNexmo send error...", e);
			return false;
		}
	}

	@Override
	protected void nextSendSms(String mSysId, String smsPrefix, String mobile, String content) {
		ycSms.send(mSysId, smsPrefix, mobile, content);
	}

}
