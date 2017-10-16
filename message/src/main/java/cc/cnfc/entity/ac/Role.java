package cc.cnfc.entity.ac;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

/**
 * 角色表
 */
@Entity
@Table(name = "m_role")
public class Role extends IdEntity {

	private static final long serialVersionUID = -8108811356160898043L;

	private String roleId;
	private String roleName;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
