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
 * nexmo营销通道
 * 
 * @author luoyb
 *
 */
@Service
@Transactional
public class SmsMarketingSendByNexmo extends SmsSendInterface {

	@Autowired
	private SmsMarketingSendByYc ycMarketingSms;

	@Override
	protected boolean sendSms(String mSysId, String smsPrefix, String mobile, String content) {
		try {
			String mobileWithPrefix = Utils.stringsAddPrefix(mobile, "86");

			String rawMessage = "【" + smsPrefix + "】" + content.trim() + " 退订回复TD";
			// String encodeMessage = URLEncoder.encode(rawMessage, "UTF-8");

			String url = "http://api.paasoo.com/json?key=ndnckk&secret=WA1VNlfQ&to=" + mobileWithPrefix + "&text="
					+ rawMessage;
			SmsResult result = new RestTemplate().getForObject(url, SmsResult.class);

			String sendStatus = "0".equals(result.getStatus()) ? SmsSendStatus.DELIVER_SUCCESS.toString()
					: SmsSendStatus.DELIVER_FAIL.toString();

			this.saveSmsLog(mSysId, mobile, rawMessage, result.getMessageid(), sendStatus, result.getStatus_code(),
					SmsSendChannel.NEXMO_MARKETING.toString());

			logger.info("sms send by nexmo marketing channel");
			return SmsSendStatus.DELIVER_FAIL.toString().equals(sendStatus) ? false : true;
		} catch (Exception e) {
			logger.error("SmsMarketingSendByNexmo send error...", e);
			return false;
		}

	}

	@Override
	protected void nextSendSms(String mSysId, String smsPrefix, String mobile, String content) {
		ycMarketingSms.send(mSysId, smsPrefix, mobile, content);
	}

	public static void main(String[] args) {
		boolean b = true || true;
		System.out.println(b);
	}

}
