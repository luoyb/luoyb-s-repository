package cc.cnfc.entity.ac;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

/**
 * 用户角色表
 */
@Entity
@Table(name = "m_customer_role")
public class CustomerRole extends IdEntity {

	private static final long serialVersionUID = -8108811356160898043L;

	private String mSysId;
	private String roleId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getmSysId() {
		return mSysId;
	}

	public void setmSysId(String mSysId) {
		this.mSysId = mSysId;
	}

}
