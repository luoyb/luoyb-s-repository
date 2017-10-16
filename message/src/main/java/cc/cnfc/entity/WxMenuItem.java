package cc.cnfc.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

@Entity
@Table(name = "m_wx_menu_item")
public class WxMenuItem extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9081852284678770212L;

	private String mSysId; //
	private String menuLevel;// 菜单等级，1、2
	private Long parentId; // 上级菜单id，空或0为没有上级
	private String menuRank; // 菜单顺序，1、2、3 ...
	private String isValid; // 是否启用，1-是，0-否
	private Long menuRuleId;// WxMenuMatchRule表的id，默认菜单为0

	private String menuType;// 菜单类型，click、view
	private String menuName;// 菜单名称
	private String replyType; // 当菜单类型为click时有用,回复类型，text、image、voice、video、music、news
	private String menuKey;// 事件key或跳转url
	private String content; // 当menuType为click时，该字段存放keyWord为menuKey，msgType为CLICK的回复内容

	// private String menuUrl;// 跳转url

	public String getmSysId() {
		return mSysId;
	}

	public void setmSysId(String mSysId) {
		this.mSysId = mSysId;
	}

	public String getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(String menuLevel) {
		this.menuLevel = menuLevel;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getMenuRank() {
		return menuRank;
	}

	public void setMenuRank(String menuRank) {
		this.menuRank = menuRank;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}

	// public String getMenuUrl() {
	// return menuUrl;
	// }
	//
	// public void setMenuUrl(String menuUrl) {
	// this.menuUrl = menuUrl;
	// }

	public Long getMenuRuleId() {
		return menuRuleId;
	}

	public void setMenuRuleId(Long menuRuleId) {
		this.menuRuleId = menuRuleId;
	}

	public String getReplyType() {
		return replyType;
	}

	public void setReplyType(String replyType) {
		this.replyType = replyType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
