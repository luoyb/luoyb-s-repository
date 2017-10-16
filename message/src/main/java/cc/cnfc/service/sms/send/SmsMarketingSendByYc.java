package cc.cnfc.service.sms.send;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.cnfc.pub.constant.Const.SmsSendChannel;
import cc.cnfc.pub.constant.Const.SmsSendStatus;

/**
 * 易驰软件营销短信平台
 * 
 * @author luoyb
 *
 */
@Service
@Transactional
public class SmsMarketingSendByYc extends SmsSendInterface {

	@Autowired
	private SmsSendByChonry chonrySms;

	/**
	 * 多个手机号码用英文逗号分割
	 */
	@Override
	protected boolean sendSms(String mSysId, String smsPrefix, String mobile, String content) {
		try {
			logger.info("sms send by yc marketing channel");

			String rawMessage = "【" + smsPrefix + "】" + content.trim() + " 退订回T";

			String resultStr = this.sendSms(mobile, rawMessage);

			String[] resultArr = resultStr.split(":");

			String sendStatus = resultStr.startsWith("SUCCESS") ? SmsSendStatus.DELIVER_SUCCESS.toString()
					: SmsSendStatus.DELIVER_FAIL.toString();

			this.saveSmsLog(mSysId, mobile, rawMessage, "", sendStatus, URLDecoder.decode(resultArr[1], "utf-8"),
					SmsSendChannel.YC_MARKETING.toString());

			return SmsSendStatus.DELIVER_FAIL.toString().equals(sendStatus) ? false : true;
		} catch (Exception e) {
			logger.error("SmsMarketingSendByYc send error...", e);
			return false;
		}
	}

	protected String smsSvcUrl = "http://115.29.47.151:8860";
	protected String cust_code = "";
	protected String password = "";

	/**
	 * 多个手机号码用英文逗号分割
	 * 
	 * @param mobiles
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public String sendSms(String mobiles, String content) throws IOException {
		return sendSms(mobiles, content, "", 0);
	}

	// public void sendSms(String mobiles, String content, long task_id)
	// throws IOException {
	// sendSms(mobiles, content, "", task_id);
	// }

	// public void sendSms(String mobiles, String content, String sp_code)
	// throws IOException {
	// sendSms(mobiles, content, sp_code, 0);
	// }

	/**
	 * 多个手机号码用英文逗号分割
	 * 
	 * @param mobiles
	 * @param content
	 * @param sp_code
	 * @param task_id
	 * @return
	 * @throws IOException
	 */
	public String sendSms(String mobiles, String content, String sp_code, long task_id) throws IOException {
		String urlencContent = URLEncoder.encode(content, "utf-8");
		String sign = getMD5((urlencContent + password));

		String postData = "content=" + urlencContent + "&destMobiles=" + mobiles + "&sign=" + sign + "&cust_code="
				+ cust_code + "&sp_code=" + sp_code + "&task_id=" + task_id;

		// System.getProperties().put("socksProxySet", "true"); //
		// System.getProperties().put("socksProxyHost", "192.168.13.19"); //
		// System.getProperties().put("socksProxyPort", "1080");//
		URL myurl = new URL(smsSvcUrl);
		URLConnection urlc = myurl.openConnection();
		urlc.setReadTimeout(1000 * 60);
		urlc.setConnectTimeout(1000);
		urlc.setDoOutput(true);
		urlc.setDoInput(true);
		urlc.setAllowUserInteraction(false);

		DataOutputStream server = new DataOutputStream(urlc.getOutputStream());
		System.out.println("发送数据=" + postData);
		int len = postData.getBytes("utf-8").length;
		server.write(postData.getBytes("utf-8"), 0, len);
		server.flush();
		server.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream(), "utf-8"));
		String resXml = "", s = "";
		while ((s = in.readLine()) != null)
			resXml = resXml + s + "\r\n";
		in.close();
		System.out.println("接收数据=" + resXml);
		return resXml;
	}

	public String getMD5(String source) {
		String md5Str = null;
		// 用来将字节转换成 16 进制表示的字符
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(source.getBytes());
			byte tmp[] = messageDigest.digest(); // MD5
													// 的计算结果是一个128位的长整数，用字节表示就是16个字节
			char str[] = new char[16 * 2]; // 每个字节用16进制表示的话，使用两个字符，所以表示成 16 进制需要
											// 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			/** 从第一个字节开始，对 MD5 的每一个字节，转换成 16 进制字符的转换 */
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换, >>>
															// 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			md5Str = new String(str); // 换后的结果转换为字符串
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5Str;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {

		// String str =
		// "%E6%8F%90%E4%BA%A4%E7%9F%AD%E4%BF%A1%E5%A4%B1%E8%B4%A5%EF%BC%8C%E5%8E%9F%E5%9B%A0%E6%98%AF%EF%BC%9A%E6%A8%A1%E7%89%88%E8%BF%87%E6%BB%A4%E5%AE%A1%E6%A0%B8%E4%B8%8D%E9%80%9A%E8%BF%87%EF%BC%81";
		//
		// String dstr = URLDecoder.decode(str, "utf-8");
		//
		// System.out.println(dstr);

		// String mobile = "123,wer";
		//
		// System.out.print(mobile.split(",|，").length);

		SmsMarketingSendByYc client = new SmsMarketingSendByYc();
		try {
			client.sendSms("18950428012", "【好海淘】今天的天气，晴，温度15-20度，事宜外出。 退订回T");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void nextSendSms(String mSysId, String smsPrefix, String mobile, String content) {
		chonrySms.send(mSysId, smsPrefix, mobile, content);
	}
}
