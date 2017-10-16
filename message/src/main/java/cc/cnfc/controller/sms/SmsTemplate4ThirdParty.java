/**
 * 
 */
package cc.cnfc.controller.sms;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.cnfc.dto.Result;
import cc.cnfc.dto.sms.SmsTemplateLsResult;
import cc.cnfc.dto.sms.SmsTemplateResult;
import cc.cnfc.entity.SmsTemplate;
import cc.cnfc.pub.constant.Const;
import cc.cnfc.pub.constant.Const.SmsTemplateStatus;
import cc.cnfc.service.MService;
import cc.cnfc.service.sms.SmsTemplateService;

/**
 * @author luoyb
 *
 */
@WebService(serviceName = "smsTemplate4ws")
@Controller
@RequestMapping(value = "/smsTemplate4tp")
public class SmsTemplate4ThirdParty {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SmsTemplateService smsTemplateSvc;
	@Autowired
	private MService mSvc;

	/**
	 * 新增或修改短信模版
	 * 
	 * @param mSysId
	 * @param timestamp
	 * @param signature
	 * @param key
	 * @param template
	 * @return
	 */
	@WebMethod
	@RequestMapping(value = "/set.do", method = RequestMethod.POST)
	public @ResponseBody Result set(String mSysId, String timestamp, String signature,
			@RequestBody SmsTemplate smsTemplate) {
		try {
			mSvc.checkSignature(mSysId, timestamp, signature);
			smsTemplateSvc.saveTemplate(mSysId, smsTemplate.getTemplateKey(), smsTemplate.getTemplateName(),
					smsTemplate.getTemplateCnt(), SmsTemplateStatus.APPLYING.toString());
			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("set template error...", e);
			return new Result(Const.NO, e.getMessage());
		}
	}

	@WebMethod
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody Result delete(String mSysId, String timestamp, String signature, @RequestBody String key) {
		try {
			mSvc.checkSignature(mSysId, timestamp, signature);
			smsTemplateSvc.delete(mSysId, key);
			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("delete template error...", e);
			return new Result(Const.NO, e.getMessage());
		}
	}

	@WebMethod
	@RequestMapping(value = "/get.do", method = RequestMethod.POST)
	public @ResponseBody SmsTemplateResult get(String mSysId, String timestamp, String signature,
			@RequestBody String key) {
		try {
			mSvc.checkSignature(mSysId, timestamp, signature);
			SmsTemplate smsTemplate = smsTemplateSvc.getLatestTemplate(mSysId, key);
			return new SmsTemplateResult(Const.YES, smsTemplate);
		} catch (Exception e) {
			logger.error("get template error...", e);
			return new SmsTemplateResult(Const.NO, e.getMessage());
		}
	}

	@WebMethod
	@RequestMapping(value = "/getAll.do", method = RequestMethod.POST)
	public @ResponseBody SmsTemplateLsResult getAll(String mSysId, String timestamp, String signature) {
		try {
			mSvc.checkSignature(mSysId, timestamp, signature);
			List<SmsTemplate> smsTemplateLs = smsTemplateSvc.getAll(mSysId);
			return new SmsTemplateLsResult(Const.YES, smsTemplateLs);
		} catch (Exception e) {
			logger.error("get all template error...", e);
			return new SmsTemplateLsResult(Const.NO, e.getMessage());
		}
	}
}
