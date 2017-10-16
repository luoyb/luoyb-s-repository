package cc.cnfc.dto.sms;

import java.util.List;

import cc.cnfc.dto.Result;
import cc.cnfc.entity.SmsTemplate;

/**
 * 获取短信多条短信模版
 * 
 * @author luoyb
 *
 */
public class SmsTemplateLsResult extends Result {
	private List<SmsTemplate> smsTemplateLs;

	public SmsTemplateLsResult(String code, String message) {
		super(code, message);
	}

	public SmsTemplateLsResult(String code, List<SmsTemplate> smsTemplateLs) {
		super(code);
		this.setSmsTemplateLs(smsTemplateLs);
	}

	public List<SmsTemplate> getSmsTemplateLs() {
		return smsTemplateLs;
	}

	public void setSmsTemplateLs(List<SmsTemplate> smsTemplateLs) {
		this.smsTemplateLs = smsTemplateLs;
	}

}
