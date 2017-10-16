package cc.cnfc.entity.ac;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

/**
 * 角色权限表
 */
@Entity
@Table(name = "m_role_permission")
public class RolePermission extends IdEntity {

	private static final long serialVersionUID = -8108811356160898043L;

	private String roleId;
	private String permissionId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

}
