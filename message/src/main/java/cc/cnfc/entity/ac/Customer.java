package cc.cnfc.entity.ac;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

/**
 * 第三方接入本系统的客户
 */
@Entity
@Table(name = "m_customer")
public class Customer extends IdEntity {

	private static final long serialVersionUID = -8108811356160898043L;

	// /////////////////////消息系统相关信息///////////////////
	private String mSysId; // 消息系统分配给第三方的唯一表示
	private String mName; // 消息系统分配给第三方的名称
	private String mPwd; // 从页面登陆消息系统的密码
	private String mToken; // 消息系统分配给第三方的token
	private String mCallBackUrl; // 第三方系统的回调地址
	private String mEnvType; // 第三方系统的环境类型，T-测试环境，P-正式环境
	private String mGetMembersUrl; // 获取第三方系统用户的接口地址

	// //////////////////////微信相关信息//////////////////////
	private String wxAppId; // 微信的appId
	private String wxAppSecret; // 微信的密钥
	private String wxToken; // 微信的token
	private String wxAesKey; // 微信的加密key
	private String wxSerialNum; // 微信号
	// //////////////////////极光相关信息/////////////////////
	private String jpushMasterSecret; // 极光密钥
	private String jpushAppKey; // 极光key
	// /////////////////////短信相关信息///////////////////
	private String smsPrefix; // 短信前缀
	private String simulateSendSms; // 模拟发送短信，1-是，其他－否

	public String getmSysId() {
		return mSysId;
	}

	public void setmSysId(String mSysId) {
		this.mSysId = mSysId;
	}

	public String getWxSerialNum() {
		return wxSerialNum;
	}

	public void setWxSerialNum(String wxSerialNum) {
		this.wxSerialNum = wxSerialNum;
	}

	public String getWxAppId() {
		return wxAppId;
	}

	public void setWxAppId(String wxAppId) {
		this.wxAppId = wxAppId;
	}

	public String getWxAppSecret() {
		return wxAppSecret;
	}

	public void setWxAppSecret(String wxAppSecret) {
		this.wxAppSecret = wxAppSecret;
	}

	public String getWxToken() {
		return wxToken;
	}

	public void setWxToken(String wxToken) {
		this.wxToken = wxToken;
	}

	public String getWxAesKey() {
		return wxAesKey;
	}

	public void setWxAesKey(String wxAesKey) {
		this.wxAesKey = wxAesKey;
	}

	public String getmToken() {
		return mToken;
	}

	public void setmToken(String mToken) {
		this.mToken = mToken;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmCallBackUrl() {
		return mCallBackUrl;
	}

	public void setmCallBackUrl(String mCallBackUrl) {
		this.mCallBackUrl = mCallBackUrl;
	}

	public String getmPwd() {
		return mPwd;
	}

	public void setmPwd(String mPwd) {
		this.mPwd = mPwd;
	}

	public String getJpushMasterSecret() {
		return jpushMasterSecret;
	}

	public void setJpushMasterSecret(String jpushMasterSecret) {
		this.jpushMasterSecret = jpushMasterSecret;
	}

	public String getJpushAppKey() {
		return jpushAppKey;
	}

	public void setJpushAppKey(String jpushAppKey) {
		this.jpushAppKey = jpushAppKey;
	}

	public String getSmsPrefix() {
		return smsPrefix;
	}

	public void setSmsPrefix(String smsPrefix) {
		this.smsPrefix = smsPrefix;
	}

	public String getmEnvType() {
		return mEnvType;
	}

	public void setmEnvType(String mEnvType) {
		this.mEnvType = mEnvType;
	}

	public String getmGetMembersUrl() {
		return mGetMembersUrl;
	}

	public void setmGetMembersUrl(String mGetMembersUrl) {
		this.mGetMembersUrl = mGetMembersUrl;
	}

	public String getSimulateSendSms() {
		return simulateSendSms;
	}

	public void setSimulateSendSms(String simulateSendSms) {
		this.simulateSendSms = simulateSendSms;
	}

}
