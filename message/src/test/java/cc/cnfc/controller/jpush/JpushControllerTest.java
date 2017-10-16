package cc.cnfc.controller.jpush;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import cc.cnfc.dto.Result;
import cc.cnfc.dto.jpush.JpushDto;

import com.beust.jcommander.internal.Maps;
import com.beust.jcommander.internal.Sets;

@Controller
@RequestMapping(value = "/tjpush")
public class JpushControllerTest {

	// @RequestMapping(value = "/testjpush2.do")
	// public @ResponseBody Result testJpush2() throws RestClientException,
	// UnsupportedEncodingException {
	// String mSysId = "ltuoyb";
	// String timestamp = "1466142279463";
	// String signature = "ce525f329028da2ad055801b991ddecee7fab3ae";
	//
	// return new RestTemplate()
	// .getForObject(
	// "http://localhost:8080/message/jpush4tp/sendPushAll.do?mSysId={mSysId}&timestamp={timestamp}&signature={signature}&alert={alert}",
	// Result.class, mSysId, timestamp, signature, "哈哈哈");
	//
	// }

	@RequestMapping(value = "/testjpush.do")
	public @ResponseBody Result testjpush() {
		String mSysId = "ltuoyb";
		String timestamp = "1466142279463";
		String signature = "ce525f329028da2ad055801b991ddecee7fab3ae";

		JpushDto jDto = new JpushDto();
		Set<String> aliass = Sets.newHashSet();
		aliass.add("18950428012");
		Map<String, String> extras = Maps.newHashMap();
		extras.put("key", "哈哈哈哈");
		extras.put("key2", "value2");
		jDto.setAlert("啦啦啦啦").setTitle("title").setAlias(aliass)
				.setExtras(extras);

		return new RestTemplate()
				.postForObject(
						"http://localhost:8080/message/jpush4tp/sendPushAlias.do?mSysId={mSysId}&timestamp={timestamp}&signature={signature}",
						jDto, Result.class, mSysId, timestamp, signature);

	}

}
