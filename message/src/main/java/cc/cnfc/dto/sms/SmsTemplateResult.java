package cc.cnfc.dto.sms;

import cc.cnfc.dto.Result;
import cc.cnfc.entity.SmsTemplate;

/**
 * 获取短信单条短信模版
 * 
 * @author luoyb
 *
 */
public class SmsTemplateResult extends Result {
	private SmsTemplate smsTemplate;

	public SmsTemplateResult(String code, String message) {
		super(code, message);
	}

	public SmsTemplateResult(String code, SmsTemplate smsTemplate) {
		super(code);
		this.smsTemplate = smsTemplate;
	}

	public SmsTemplate getSmsTemplate() {
		return smsTemplate;
	}

	public void setSmsTemplate(SmsTemplate smsTemplate) {
		this.smsTemplate = smsTemplate;
	}
}
