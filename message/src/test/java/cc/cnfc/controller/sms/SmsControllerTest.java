package cc.cnfc.controller.sms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import cc.cnfc.dto.Result;
import cc.cnfc.dto.sms.SmsDto;

@Controller
@RequestMapping(value = "/tsms")
public class SmsControllerTest {

	@RequestMapping(value = "/testSms.do")
	public @ResponseBody Result testSms() {
		String mSysId = "luoyb09test53dc";
		String timestamp = "1466142279463";
		String signature = "91ed69714e7bfc2cf8391e9e0ee09880f2e67447";

		SmsDto dto = new SmsDto();
		dto.setMobile("13950140033,13338294747,13860455422,15260056689,13435318335");
		dto.setContent("恭喜您抽中“满150减20优惠券”1张，请登录好海淘“我的优惠券”查看！好海淘618火爆大促，啤酒每罐低至2.4元起，点我 http://m.hht618.com 查看。");
		return new RestTemplate()
				.postForObject(
						"http://localhost:8080/message/sms4tp/sendSms.do?mSysId={mSysId}&timestamp={timestamp}&signature={signature}",
						dto, Result.class, mSysId, timestamp, signature);

	}

	@RequestMapping(value = "/testSms2.do")
	public @ResponseBody Result testSms2() {
		String mSysId = "luoyb09test53dc";
		String timestamp = "1466142279463";
		String signature = "91ed69714e7bfc2cf8391e9e0ee09880f2e67447";

		SmsDto dto = new SmsDto();
		dto.setMobile("15080318757,15980930401,13338294747,18046043465,13554929877");
		dto.setContent("恭喜您抽中“满100减10优惠券”1张，请登录好海淘“我的优惠券”查看！好海淘618火爆大促，啤酒每罐低至2.4元起，点我 http://m.hht618.com 查看。");
		return new RestTemplate()
				.postForObject(
						"http://localhost:8080/message/sms4tp/sendSms.do?mSysId={mSysId}&timestamp={timestamp}&signature={signature}",
						dto, Result.class, mSysId, timestamp, signature);

	}

	@RequestMapping(value = "/testSms3.do")
	public @ResponseBody Result testSms3() {
		String mSysId = "luoyb09test53dc";
		String timestamp = "1466142279463";
		String signature = "91ed69714e7bfc2cf8391e9e0ee09880f2e67447";

		SmsDto dto = new SmsDto();
		dto.setMobile("18906050699");
		dto.setContent("恭喜您抽中“满50减5优惠券”1张，请登录好海淘“我的优惠券”查看！好海淘618火爆大促，啤酒每罐低至2.4元起，点我 http://m.hht618.com 查看。");
		return new RestTemplate()
				.postForObject(
						"http://localhost:8080/message/sms4tp/sendSms.do?mSysId={mSysId}&timestamp={timestamp}&signature={signature}",
						dto, Result.class, mSysId, timestamp, signature);

	}
}
