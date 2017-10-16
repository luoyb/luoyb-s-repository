/**
 * 
 */
package cc.cnfc.dto;

/**
 * @author luoyb 签名验证dto
 */
public class MAuthDto {

	private String mSysId;
	private String timestamp;
	private String signature;

	public String getmSysId() {
		return mSysId;
	}

	public void setmSysId(String mSysId) {
		this.mSysId = mSysId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
