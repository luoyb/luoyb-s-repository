package cc.cnfc.service.wx;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.bean.WxMenu.WxMenuButton;
import me.chanjar.weixin.common.bean.WxMenu.WxMenuRule;
import me.chanjar.weixin.common.util.StringUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.cnfc.core.orm.Page;
import cc.cnfc.core.orm.hibernate.HibernatePage;
import cc.cnfc.core.service.BaseService;
import cc.cnfc.dao.WxMenuItemDao;
import cc.cnfc.entity.WxMenuItem;
import cc.cnfc.entity.WxMenuMatchRule;
import cc.cnfc.entity.WxReplyMsg;
import cc.cnfc.pub.config.MyProperties;
import cc.cnfc.pub.constant.Const;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Lists;

@Service
@Transactional
public class WxMenuService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -644134509331536259L;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WxService wxSvc;
	@Autowired
	private WxMenuItemDao wxMenuItemDao;
	@Autowired
	private WxMenuMatchRuleService wxMenuMatchRuleService;
	@Autowired
	private WxReplyMsgService wxReplyMsgSvc;
	@Autowired
	private MyProperties myProperties;

	public static final String MENU_TYPE_CLICK = "click";
	public static final String MENU_TYPE_VIEW = "view";

	public Page conditionalMenuRuleList(String mSysId) {
		HibernatePage hibernatePage = new HibernatePage();
		hibernatePage.setQueryClass(WxMenuMatchRule.class);
		hibernatePage.setConditionEqual("mSysId", mSysId);
		Page page = this.query4JqGrid(hibernatePage);
		return page;
	}

	public void saveWxMenu(String mSysId, List<WxMenuItem> wxMenuItemLs) {
		for (WxMenuItem item : wxMenuItemLs) {
			String content = null;
			if (WxMenuService.MENU_TYPE_CLICK.equals(item.getMenuType())) {
				content = item.getMenuKey();
				List<WxReplyMsg> replyLs = wxReplyMsgSvc.find(mSysId, WxConsts.EVT_CLICK, item.getReplyType(), content);
				String keyWord = UUID.randomUUID().toString();
				if (CollectionUtils.isNotEmpty(replyLs)) {
					keyWord = replyLs.get(0).getKeyWord();
				}
				wxReplyMsgSvc.saveMenuClickReply(mSysId, "", keyWord, item.getReplyType(), item.getMenuKey());
				item.setMenuKey(keyWord);

			}
			this.updateWxMenuItem(item.getId(), item.getIsValid(), item.getMenuType(), item.getMenuName(),
					item.getReplyType(), item.getMenuKey(), content);
		}
	}

	/**
	 * 更新菜单
	 * 
	 * @param id
	 * @param isValid
	 * @param menuType
	 * @param menuName
	 * @param replyType
	 * @param menuKey
	 */
	public void updateWxMenuItem(Long id, String isValid, String menuType, String menuName, String replyType,
			String menuKey, String content) {
		WxMenuItem item = wxMenuItemDao.get(id);
		item.setIsValid(isValid);
		item.setMenuType(menuType);
		item.setMenuName(menuName);
		item.setReplyType(replyType);
		item.setMenuKey(menuKey);
		item.setContent(content);
		wxMenuItemDao.save(item);
	}

	public List<WxMenuItem> wxMenuList(String mSysId, Long menuRuleId) {
		List<WxMenuItem> wxMenuItemLs = wxMenuItemDao.find(
				"from WxMenuItem where mSysId=? and menuRuleId=? order by id", mSysId, menuRuleId);
		if (CollectionUtils.isEmpty(wxMenuItemLs)) {
			wxMenuItemLs = this.initMenu2Db(mSysId, menuRuleId);
		}
		return wxMenuItemLs;
	}

	/*
	 * 初始化菜单到数据库
	 */
	public List<WxMenuItem> initMenu2Db(String mSysId, Long menuRuleId) {
		List<WxMenuItem> wxMenuItemLs = Lists.newArrayList();
		for (int i = 1; i <= 3; i++) {
			WxMenuItem item = new WxMenuItem();
			item.setmSysId(mSysId);
			item.setMenuLevel(Const.WX_MENU_LEVEL_1);
			item.setParentId(0L);
			item.setMenuRank(String.valueOf(i));
			item.setIsValid(Const.NO);
			item.setMenuRuleId(menuRuleId);
			item.setMenuName("默认菜单" + i);
			wxMenuItemDao.save(item);
			wxMenuItemLs.add(item);
			for (int j = 1; j <= 5; j++) {
				WxMenuItem subItem = new WxMenuItem();
				subItem.setmSysId(mSysId);
				subItem.setMenuLevel(Const.WX_MENU_LEVEL_2);
				subItem.setParentId(item.getId());
				subItem.setMenuRank(String.valueOf(j));
				subItem.setIsValid(Const.NO);
				subItem.setMenuRuleId(menuRuleId);
				subItem.setMenuType(MENU_TYPE_VIEW);
				subItem.setMenuName("默认子菜单" + j);
				subItem.setMenuKey("http://");
				wxMenuItemDao.save(subItem);
				wxMenuItemLs.add(subItem);
			}
		}
		return wxMenuItemLs;
	}

	// public List<WxMenuItem> defaultWxMenuList(String mSysId) {
	// List<WxMenuItem> wxMenuItemLs = wxMenuItemDao.find(
	// "from WxMenuItem where mSysId=? and menuRuleId=0 order by id", mSysId);
	// return wxMenuItemLs;
	// }

	/**
	 * 获取菜单
	 * 
	 * @return
	 */
	public WxMenu getWxMenu(String mSysId, Long menuRuleId) {
		WxMenu wxMenu = new WxMenu();
		List<WxMenuButton> buttons = new ArrayList<WxMenuButton>();

		List<WxMenuItem> menuItemLs = wxMenuItemDao.find(
				"from WxMenuItem where mSysId=? and menuRuleId=? and isValid='1' order by menuLevel,parentId,menuRank",
				mSysId, menuRuleId);
		List<WxMenuItem> firstMenuItemLs = this.filterWxMenuItem(menuItemLs, Const.WX_MENU_LEVEL_1, null);
		for (WxMenuItem menuItem : firstMenuItemLs) {
			List<WxMenuItem> secondMenuItemLs = this.filterWxMenuItem(menuItemLs, Const.WX_MENU_LEVEL_2,
					menuItem.getId());
			List<WxMenuButton> menuButtonLs = this.wxMenuItemLs2WxMenuButtonLs(secondMenuItemLs);
			WxMenuButton menuButton = this.wxMenuItem2WxMenuButton(menuItem, menuButtonLs);
			buttons.add(menuButton);
		}
		wxMenu.setButtons(buttons);
		WxMenuMatchRule menuMatchRule = wxMenuMatchRuleService.load(WxMenuMatchRule.class, menuRuleId);
		if (menuMatchRule != null) {
			WxMenuRule wxMenuRule = this.wxMenuMatchRule2WxMenuRule(menuMatchRule);
			logger.info(JSON.toJSONString(wxMenuRule));
			wxMenu.setMatchRule(wxMenuRule);
		}
		return wxMenu;
	}

	private WxMenuRule wxMenuMatchRule2WxMenuRule(WxMenuMatchRule menuMatchRule) {
		WxMenuRule wxMenuRule = new WxMenuRule();
		wxMenuRule.setCity(menuMatchRule.getCity());
		wxMenuRule.setClient_platform_type(menuMatchRule.getClientPlatformType());
		wxMenuRule.setCountry(menuMatchRule.getCountry());
		wxMenuRule.setGroup_id(menuMatchRule.getGroupId());
		wxMenuRule.setProvince(menuMatchRule.getProvince());
		wxMenuRule.setSex(menuMatchRule.getSex());
		return wxMenuRule;
	}

	private List<WxMenuButton> wxMenuItemLs2WxMenuButtonLs(List<WxMenuItem> menuItemLs) {
		List<WxMenuButton> menuButtonLs = Lists.newArrayList();
		for (WxMenuItem menuItem : menuItemLs) {
			WxMenuButton menuButton = this.wxMenuItem2WxMenuButton(menuItem, null);
			menuButtonLs.add(menuButton);
		}
		return menuButtonLs;
	}

	private WxMenuButton wxMenuItem2WxMenuButton(WxMenuItem menuItem, List<WxMenuButton> subButtons) {
		WxMenuButton button = new WxMenuButton();
		button.setKey(menuItem.getMenuKey());
		button.setName(menuItem.getMenuName());
		button.setType(menuItem.getMenuType());
		button.setUrl(menuItem.getMenuKey());
		if (CollectionUtils.isNotEmpty(subButtons)) {
			button.setSubButtons(subButtons);
		}
		return button;
	}

	@SuppressWarnings("unchecked")
	public List<WxMenuItem> filterWxMenuItem(List<WxMenuItem> menuItemLs, final String menuLevel, final Long parentId) {
		List<WxMenuItem> newMenuItemLs = Lists.newArrayList(menuItemLs);
		if (StringUtils.isNotBlank(menuLevel)) {
			Predicate predicate = new Predicate() {
				@Override
				public boolean evaluate(Object arg0) {
					WxMenuItem menuItem = (WxMenuItem) arg0;
					if (menuLevel.equals(menuItem.getMenuLevel())) {
						return true;
					}
					return false;
				}
			};
			CollectionUtils.filter(newMenuItemLs, predicate);
		}
		if (parentId != null) {
			Predicate predicate = new Predicate() {
				@Override
				public boolean evaluate(Object arg0) {
					WxMenuItem menuItem = (WxMenuItem) arg0;
					if (parentId.equals(menuItem.getParentId())) {
						return true;
					}
					return false;
				}
			};
			CollectionUtils.filter(newMenuItemLs, predicate);
		}
		return newMenuItemLs;
	}

	/**
	 * 获取第一个1级菜单
	 * 
	 * @return
	 */
	private WxMenuButton getMenuButton1(String mSysId) {
		List<WxMenuButton> subButtons = new ArrayList<WxMenuButton>();
		WxMenuButton subButton = new WxMenuButton();
		subButton.setType(WxConsts.BUTTON_VIEW);
		subButton.setName("我的信息");
		String url = wxSvc.getWxMpService(mSysId).oauth2buildAuthorizationUrl(
				myProperties.getSysDomain() + "/wx/findMyWxInfo.do?mSysId=" + mSysId, WxConsts.OAUTH2_SCOPE_USER_INFO,
				"123");
		logger.info(url);
		subButton.setUrl(url);
		subButtons.add(subButton);

		WxMenuButton button = new WxMenuButton();
		button.setType(WxConsts.BUTTON_CLICK);
		button.setName("信息");
		button.setKey("INFO");
		button.setSubButtons(subButtons);
		return button;
	}

	/**
	 * 获取第二个1级菜单
	 * 
	 * @return
	 */
	private WxMenuButton getMenuButton2() {
		List<WxMenuButton> subButtons = new ArrayList<WxMenuButton>();
		WxMenuButton subButton = new WxMenuButton();
		subButton.setType(WxConsts.BUTTON_VIEW);
		subButton.setName("豆瓣大神");
		subButton.setUrl("http://www.douban.com");
		subButtons.add(subButton);
		WxMenuButton subButton2 = new WxMenuButton();
		subButton2.setType(WxConsts.BUTTON_VIEW);
		subButton2.setName("知乎大神");
		subButton2.setUrl("http://www.zhihu.com");
		subButtons.add(subButton2);

		WxMenuButton button = new WxMenuButton();
		button.setType(WxConsts.BUTTON_CLICK);
		button.setName("最爱网站");
		button.setKey("FAVORITE_WEB");
		button.setSubButtons(subButtons);
		return button;
	}

}
