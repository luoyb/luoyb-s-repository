/**
 * 
 */
package cc.cnfc.controller.jpush;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.cnfc.dto.MqDto;
import cc.cnfc.dto.Result;
import cc.cnfc.dto.jpush.JpushDto;
import cc.cnfc.pub.constant.Const;
import cc.cnfc.pub.constant.Const.JushAudienceType;
import cc.cnfc.pub.constant.Const.MqMsgType;
import cc.cnfc.service.MService;
import cc.cnfc.service.MqService;

/**
 * @author luoyb
 *
 */
// @WebService(serviceName = "jpush4ws")
@Controller
@RequestMapping(value = "/jpush4tp")
public class Jpush4ThirdParty {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MService mSvc;
	@Autowired
	private MqService mqSvc;

	/**
	 * 向所有人发送通知
	 */
	@RequestMapping(value = "/sendPushAll.do")
	public @ResponseBody Result sendPushAll(String mSysId, String timestamp,
			String signature, @RequestBody String alert) {
		try {
			mSvc.checkSignature(mSysId, timestamp, signature);
			JpushDto jDto = new JpushDto().setAlert(alert)
					.setJpushAudienceType(JushAudienceType.ALL);
			mqSvc.enqueue(new MqDto<JpushDto>(mSysId, MqMsgType.JPUSH, jDto));
			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("sendPushAll error...", e);
			return new Result(Const.NO, e.getMessage());
		}
	}

	/**
	 * 向指定的别名发送通知
	 */
	@RequestMapping(value = "/sendPushAlias.do")
	public @ResponseBody Result sendPushAlias(String mSysId, String timestamp,
			String signature, @RequestBody JpushDto jDto) {
		try {
			mSvc.checkSignature(mSysId, timestamp, signature);
			jDto.setJpushAudienceType(JushAudienceType.ALIAS);
			mqSvc.enqueue(new MqDto<JpushDto>(mSysId, MqMsgType.JPUSH, jDto));
			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("sendPushAlias error...", e);
			return new Result(Const.NO, e.getMessage());
		}
	}

	// public static void main(String[] args) {
	// Jpush4ThirdParty jpush4ThirdParty = new Jpush4ThirdParty();
	// Set<String> aliass = Sets.newHashSet();
	// aliass.add("18950428012");
	// Map<String, String> extras = Maps.newHashMap();
	// extras.put("key", "value");
	// // jpush4ThirdParty.sendPushAlias(aliass, "title", "alert", extras);
	// }
}
