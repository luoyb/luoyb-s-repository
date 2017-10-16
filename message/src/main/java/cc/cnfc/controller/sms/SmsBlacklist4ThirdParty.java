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
import cc.cnfc.dto.sms.SmsBlacklistLsResult;
import cc.cnfc.entity.SmsBlacklist;
import cc.cnfc.pub.constant.Const;
import cc.cnfc.service.MService;
import cc.cnfc.service.sms.SmsBlacklistService;

/**
 * @author luoyb
 *
 */
@WebService(serviceName = "smsBlacklist4ws")
@Controller
@RequestMapping(value = "/smsBlacklist4tp")
public class SmsBlacklist4ThirdParty {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SmsBlacklistService smsBlacklistSvc;
	@Autowired
	private MService mSvc;

	@WebMethod
	@RequestMapping(value = "/add.do", method = RequestMethod.POST)
	public @ResponseBody Result add(String mSysId, String timestamp, String signature,
			@RequestBody SmsBlacklist smsBlacklist) {
		try {
			mSvc.checkSignature(mSysId, timestamp, signature);
			smsBlacklistSvc.add(mSysId, smsBlacklist.getMobiles(), smsBlacklist.getReason());
			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("add blacklist error...", e);
			return new Result(Const.NO, e.getMessage());
		}
	}

	@WebMethod
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public @ResponseBody Result update(String mSysId, String timestamp, String signature,
			@RequestBody SmsBlacklist smsBlacklist) {
		try {
			mSvc.checkSignature(mSysId, timestamp, signature);
			smsBlacklistSvc.update(mSysId, smsBlacklist.getId(), smsBlacklist.getMobiles(), smsBlacklist.getReason());
			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("update blacklist error...", e);
			return new Result(Const.NO, e.getMessage());
		}
	}

	@WebMethod
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public @ResponseBody Result delete(String mSysId, String timestamp, String signature,
			@RequestBody SmsBlacklist smsBlacklist) {
		try {
			mSvc.checkSignature(mSysId, timestamp, signature);
			smsBlacklistSvc.delete(mSysId, smsBlacklist.getId());
			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("delete blacklist error...", e);
			return new Result(Const.NO, e.getMessage());
		}
	}

	@WebMethod
	@RequestMapping(value = "/getAll.do", method = RequestMethod.POST)
	public @ResponseBody SmsBlacklistLsResult getAll(String mSysId, String timestamp, String signature) {
		try {
			mSvc.checkSignature(mSysId, timestamp, signature);
			List<SmsBlacklist> blacklistLs = smsBlacklistSvc.getAll(mSysId);
			return new SmsBlacklistLsResult(Const.YES, blacklistLs);
		} catch (Exception e) {
			logger.error("get all blacklist error...", e);
			return new SmsBlacklistLsResult(Const.NO, e.getMessage());
		}
	}

}
