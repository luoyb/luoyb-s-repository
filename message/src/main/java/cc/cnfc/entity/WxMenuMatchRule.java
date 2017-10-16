package cc.cnfc.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

@Entity
@Table(name = "m_wx_menu_match_rule")
public class WxMenuMatchRule extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -590843698561948168L;

	private String mSysId;
	private String ruleName;
	private String groupId;
	// private String groupName;
	private String sex;
	private String country;
	private String province;
	private String city;
	private String clientPlatformType;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getClientPlatformType() {
		return clientPlatformType;
	}

	public void setClientPlatformType(String clientPlatformType) {
		this.clientPlatformType = clientPlatformType;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	// public String getGroupName() {
	// return groupName;
	// }
	//
	// public void setGroupName(String groupName) {
	// this.groupName = groupName;
	// }

	public String getmSysId() {
		return mSysId;
	}

	public void setmSysId(String mSysId) {
		this.mSysId = mSysId;
	}

}
