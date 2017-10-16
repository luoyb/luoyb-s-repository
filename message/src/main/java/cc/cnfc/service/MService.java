package cc.cnfc.service;

import java.security.NoSuchAlgorithmException;

import me.chanjar.weixin.common.util.crypto.SHA1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.cnfc.entity.ac.Customer;

@Service
@Transactional
public class MService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustomerService customerInfoSvc;

	/**
	 * 系统接口验证
	 * 
	 * @param appId
	 * @param timestamp
	 * @param signature
	 * @return
	 */
	public String checkSignature(String mSysId, String timestamp, String signature) {
		try {
			Customer ci = customerInfoSvc.findByMSysId(mSysId);
			String createSignature = SHA1.gen(ci.getmToken(), mSysId, timestamp);
			if (!createSignature.equals(signature)) {
				throw new RuntimeException("接入消息系统验证失败");
			}
			return ci.getmSysId();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public String createSignature(Customer ci, String timestamp) {
		try {
			return SHA1.gen(ci.getmToken(), ci.getmSysId(), timestamp);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
