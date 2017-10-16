package cc.cnfc.entity.ac;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

/**
 * 权限表
 */
@Entity
@Table(name = "m_permission")
public class permission extends IdEntity {

	private static final long serialVersionUID = -8108811356160898043L;

	private String permissionId;
	private String permissionName;

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

}
