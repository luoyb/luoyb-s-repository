package cc.cnfc.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

/**
 * 短信黑名单
 * 
 * @author luoyb
 *
 */
@Entity
@Table(name = "m_sms_blacklist")
public class SmsBlacklist extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3318761862126376606L;

	private String mSysId;
	private String mobiles; // 手机号，多个用英文逗号分割
	private String reason; // 拉黑原因
	private Date createTime; // 创建时间
	private Date updateTime; // 修改时间

	public String getmSysId() {
		return mSysId;
	}

	public void setmSysId(String mSysId) {
		this.mSysId = mSysId;
	}

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
