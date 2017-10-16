package cc.cnfc.service.sms.send;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import cc.cnfc.pub.constant.Const.SmsSendChannel;
import cc.cnfc.pub.constant.Const.SmsSendStatus;

/**
 * chonry短信发送通道
 * 
 * @author luoyb
 *
 */
@Service
@Transactional
public class SmsSendByChonry extends SmsSendInterface {

	@Override
	protected boolean sendSms(String mSysId, String smsPrefix, String mobile, String content) {
		try {
			logger.info("sms send by chonry channel");

			String rawMessage = "【" + smsPrefix + "】" + content;
			if (mobile.split(",|，").length > 1) {
				rawMessage += " 回T退订";
			}

			// String sign="好海淘";
			// 创建StringBuffer对象用来操作字符串
			StringBuffer sb = new StringBuffer("http://web.cr6868.com/asmx/smsservice.aspx?");
			// 向StringBuffer追加用户名
			sb.append("name=");
			// 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
			sb.append("&pwd=");
			// 向StringBuffer追加手机号码
			sb.append("&mobile=" + mobile);
			// 向StringBuffer追加消息内容转URL标准码
			// sb.append("&content=" + URLEncoder.encode(rawMessage, "utf-8"));
			sb.append("&content=" + rawMessage);
			// 追加发送时间，可为空，为空为及时发送
			sb.append("&stime=");
			// 加签名
			// sb.append("&sign="+URLEncoder.encode(sign,"utf-8"));
			// type为固定值pt extno为扩展码，必须为数字 可为空
			sb.append("&type=pt&extno=");

			String returnStr = new RestTemplate().getForObject(sb.toString(), String.class);

			// 返回结果为‘0，20140009090990,1，提交成功’ 发送成功 具体见说明文档
			String[] results = returnStr.split(",");

			String sendStatus = "0".equals(results[0]) ? SmsSendStatus.DELIVER_SUCCESS.toString()
					: SmsSendStatus.DELIVER_FAIL.toString();
			this.saveSmsLog(mSysId, mobile, rawMessage, results[1], sendStatus, results[0],
					SmsSendChannel.CHONRY.toString());

			return SmsSendStatus.DELIVER_FAIL.toString().equals(sendStatus) ? false : true;
		} catch (Exception e) {
			logger.error("SmsSendByChonry send error...", e);
			return false;
		}
	}

	@Override
	protected void nextSendSms(String mSysId, String smsPrefix, String mobile, String content) {
		return;
	}
}
