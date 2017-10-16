package cc.cnfc.controller.wx;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import cc.cnfc.dto.Result;
import cc.cnfc.dto.wx.WxTextDto;

@Controller
@RequestMapping(value = "/twx")
public class WxControllerTest {

	@RequestMapping(value = "/testCreateMenu.do")
	public @ResponseBody void testCreateMenu() {
		String mSysId = "htunion";
		String timestamp = "1466142279463";
		String signature = "ce525f329028da2ad055801b991ddecee7fab3ae";

		new RestTemplate()
				.postForObject(
						"http://localhost:8080/message/wx4tp/createMenu.do?mSysId={mSysId}&timestamp={timestamp}&signature={signature}",
						null, Result.class, mSysId, timestamp, signature);
	}

	@RequestMapping(value = "/testText.do")
	public @ResponseBody void testText() {
		String mSysId = "ltuoyb";
		String timestamp = "1466142279463";
		String signature = "ce525f329028da2ad055801b991ddecee7fab3ae";

		WxTextDto tDto = new WxTextDto();
		tDto.setContent("你好");
		tDto.setOpenId("ovjV9wLdoX6-H0aT4ybnvFYazihE");
		new RestTemplate()
				.postForObject(
						"http://localhost:8080/message/wx4tp/sendText.do?mSysId={mSysId}&timestamp={timestamp}&signature={signature}",
						tDto, Result.class, mSysId, timestamp, signature);

		WxTextDto tDto1 = new WxTextDto();
		tDto1.setContent("我好");
		tDto1.setOpenId("ovjV9wLdoX6-H0aT4ybnvFYazihE");
		new RestTemplate()
				.postForObject(
						"http://localhost:8080/message/wx4tp/sendText.do?mSysId={mSysId}&timestamp={timestamp}&signature={signature}",
						tDto1, Result.class, mSysId, timestamp, signature);

	}

	// @RequestMapping(value = "/testNews.do")
	// public @ResponseBody Result testNews() {
	// String mSysId = "ltuoyb";
	// String timestamp = "1466142279463";
	// String signature = "ce525f329028da2ad055801b991ddecee7fab3ae";
	//
	// WxNewsDto nDto = new WxNewsDto();
	// nDto.setOpenId("ovjV9wLdoX6-H0aT4ybnvFYazihE");
	// nDto.setUrl("http://m.hht618.com/pageindex.html?pageId=196");
	// //
	// nDto.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/07LMdz7O8dF8Hf3DCYRCePuYHPL2VZu76u3ZVL85MfFticTKqeNIuINQrl4STCq3ScPHS2Uzd96l3gLZvDm5vHA/0?wx_fmt=jpeg");
	// nDto.setTitle("中秋特惠");
	// nDto.setDesc("【中秋推荐】中秋好利来～美心月饼大促，全场6折起，下单立减100元！");
	//
	// return new RestTemplate()
	// .postForObject(
	// "http://localhost:8080/message/wx4tp/sendNews.do?mSysId={mSysId}&timestamp={timestamp}&signature={signature}",
	// nDto, Result.class, mSysId, timestamp, signature);
	//
	// }

	@RequestMapping(value = "/testMassNews.do")
	public @ResponseBody Result testMassNews() {
		String mSysId = "ltuoyb";
		String timestamp = "1466142279463";
		String signature = "ce525f329028da2ad055801b991ddecee7fab3ae";

		return new RestTemplate()
				.getForObject(
						"http://localhost:8080/message/wx4tp/sendMassNews.do?mSysId={mSysId}&timestamp={timestamp}&signature={signature}",
						Result.class, mSysId, timestamp, signature);
	}
}
