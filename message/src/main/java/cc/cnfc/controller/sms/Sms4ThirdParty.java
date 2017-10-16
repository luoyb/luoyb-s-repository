/**
 * 
 */
package cc.cnfc.controller.sms;

import javax.jws.WebMethod;
import javax.jws.WebService;

import me.chanjar.weixin.common.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.cnfc.dto.Result;
import cc.cnfc.dto.sms.SmsDto;
import cc.cnfc.pub.constant.Const;
import cc.cnfc.pub.constant.Const.SmsSendChannel;
import cc.cnfc.pub.constant.Const.SmsType;
import cc.cnfc.service.MService;
import cc.cnfc.service.sms.SmsService;
import cc.cnfc.service.sms.SmsTemplateService;

/**
 * @author luoyb
 *
 */
@WebService(serviceName = "sms4ws")
@Controller
@RequestMapping(value = "/sms4tp")
public class Sms4ThirdParty {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SmsService smsSvc;
	@Autowired
	private SmsTemplateService smsTemplateSvc;
	@Autowired
	private MService mSvc;

	@WebMethod
	@RequestMapping(value = "/sendSms.do", method = RequestMethod.POST)
	public @ResponseBody Result sendSms(String mSysId, String timestamp, String signature, @RequestBody SmsDto sDto) {
		try {
			mSvc.checkSignature(mSysId, timestamp, signature);

			if (StringUtils.isNotBlank(sDto.getTemplateKey()) && sDto.getDatas() != null) {
				String smsCnt = smsTemplateSvc.paddingSmsTemplate(mSysId, sDto.getTemplateKey(), sDto.getDatas());
				sDto.setContent(smsCnt);
			}

			if (StringUtils.isNotBlank(sDto.getTemplateKey()) && sDto.getDataMap() != null) {
				String smsCnt = smsTemplateSvc.paddingSmsTemplate(mSysId, sDto.getTemplateKey(), sDto.getDataMap());
				sDto.setContent(smsCnt);
			}

			if (StringUtils.isNotBlank(sDto.getSmsType())) {
				if (SmsType.BIZ.toString().equals(sDto.getSmsType())) {
					sDto.setSendChannel(SmsSendChannel.NEXMO_STRICT.toString());
				}
				if (SmsType.MARKETING.toString().equals(sDto.getSmsType())) {
					sDto.setSendChannel(SmsSendChannel.NEXMO_MARKETING.toString());
				}
			}

			smsSvc.enqueueSms(mSysId, sDto);
			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("sendSms error...", e);
			return new Result(Const.NO, e.getMessage());
		}
	}
}
