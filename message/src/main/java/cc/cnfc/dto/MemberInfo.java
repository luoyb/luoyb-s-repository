package cc.cnfc.dto;

/**
 * 用户信息
 * 
 * @author luoyb
 * @date May 17, 2016
 * @version $Revision$
 */
public class MemberInfo {
	/**
	 * 
	 */
	private String memberId;
	private String mobile;
	private String name;
	private String openId;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
