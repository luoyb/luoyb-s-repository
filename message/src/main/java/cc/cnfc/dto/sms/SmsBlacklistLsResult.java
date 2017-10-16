package cc.cnfc.dto.sms;

import java.util.List;

import cc.cnfc.dto.Result;
import cc.cnfc.entity.SmsBlacklist;

/**
 * 短信黑名单列表
 * 
 * @author luoyb
 *
 */
public class SmsBlacklistLsResult extends Result {
	private List<SmsBlacklist> smsBlacklistLs;

	public SmsBlacklistLsResult(String code, String message) {
		super(code, message);
	}

	public SmsBlacklistLsResult(String code, List<SmsBlacklist> smsBlacklistLs) {
		super(code);
		this.setSmsBlacklistLs(smsBlacklistLs);
	}

	public List<SmsBlacklist> getSmsBlacklistLs() {
		return smsBlacklistLs;
	}

	public void setSmsBlacklistLs(List<SmsBlacklist> smsBlacklistLs) {
		this.smsBlacklistLs = smsBlacklistLs;
	}

}
