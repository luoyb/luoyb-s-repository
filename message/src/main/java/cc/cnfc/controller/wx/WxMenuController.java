package cc.cnfc.controller.wx;

import java.util.List;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.mp.bean.WxMpGroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cc.cnfc.core.orm.Page;
import cc.cnfc.dto.Result;
import cc.cnfc.entity.WxMenuItem;
import cc.cnfc.entity.WxMenuMatchRule;
import cc.cnfc.entity.ac.Customer;
import cc.cnfc.pub.constant.Const;
import cc.cnfc.service.CustomerService;
import cc.cnfc.service.wx.WxMenuMatchRuleService;
import cc.cnfc.service.wx.WxMenuService;
import cc.cnfc.service.wx.WxReplyMsgService;
import cc.cnfc.service.wx.WxService;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = "/wxMenu")
public class WxMenuController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WxService wxSvc;
	@Autowired
	private WxMenuService wxMenuSvc;
	@Autowired
	private CustomerService customerSvc;
	@Autowired
	private WxReplyMsgService wxReplyMsgSvc;
	@Autowired
	private WxMenuMatchRuleService wxMenuMatchRuleSvc;

	public static long DEFAULT_MENU_RULE_ID = 0L;

	@RequestMapping(value = "/createMenu.do")
	public @ResponseBody Result createMenu(Long menuRuleId) {
		try {
			Customer customer = customerSvc.findLoginCustomer();
			wxSvc.getWxMpService(customer.getmSysId())
					.menuCreate(wxMenuSvc.getWxMenu(customer.getmSysId(), menuRuleId));
			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("createMenu error", e);
			return new Result(Const.NO, e.getMessage());
		}
	}

	@RequestMapping(value = "/toWxConditionalMenuRuleList.do")
	public ModelAndView toWxConditionalMenuRuleList() throws WxErrorException {
		ModelAndView mv = new ModelAndView();
		Customer customer = customerSvc.findLoginCustomer();
		List<WxMpGroup> wxGroupLs = wxSvc.getWxMpService(customer.getmSysId()).groupGet();
		StringBuffer groupStr = new StringBuffer();
		for (WxMpGroup group : wxGroupLs) {
			if (groupStr.length() > 0) {
				groupStr.append(";");
			}
			groupStr.append(group.getId() + ":" + group.getName());
		}
		mv.addObject("wxGroupStr", groupStr.toString());
		mv.setViewName("wx/conditional-menu-rule/wrap_wx_conditional_menu_rule");
		return mv;
	}

	@RequestMapping(value = "/conditionalMenuRuleList.do")
	public @ResponseBody String conditionalMenuRuleList() {
		try {
			Customer customer = customerSvc.findLoginCustomer();
			Page page = wxMenuSvc.conditionalMenuRuleList(customer.getmSysId());
			return page.getJQGridJsonString();
		} catch (Exception e) {
			logger.error("WxMenuController.conditionalMenuRuleList error...", e);
			return null;
		}
	}

	@RequestMapping(value = "/conditionalMenuRuleOper.do")
	public @ResponseBody void conditionalMenuRuleOper(String id, String ruleName, String groupId, String oper)
			throws Exception {
		Customer customer = customerSvc.findLoginCustomer();
		if ("add".equals(oper)) {
			WxMenuMatchRule rule = new WxMenuMatchRule();
			rule.setmSysId(customer.getmSysId());
			rule.setRuleName(ruleName);
			rule.setGroupId(groupId);
			wxMenuMatchRuleSvc.add(rule);
		}
		if ("edit".equals(oper)) {
			WxMenuMatchRule rule = wxMenuMatchRuleSvc.load(WxMenuMatchRule.class, Long.valueOf(id));
			rule.setRuleName(ruleName);
			rule.setGroupId(groupId);
			wxMenuMatchRuleSvc.add(rule);
		}
		if ("del".equals(oper)) {
			String[] idArr = id.split(",");
			for (String _id : idArr) {
				if (StringUtils.isNotBlank(_id)) {
					WxMenuMatchRule rule = wxMenuMatchRuleSvc.load(WxMenuMatchRule.class, Long.valueOf(_id));
					wxMenuMatchRuleSvc.delete(rule);
				}
			}
		}
	}

	@RequestMapping(value = "/toWxConditionalMenuList.do")
	public ModelAndView toWxConditionalMenuList(long menuRuleId) {
		ModelAndView mv = new ModelAndView();
		Customer customer = customerSvc.findLoginCustomer();
		List<WxMenuItem> wxMenuItemLs = wxMenuSvc.wxMenuList(customer.getmSysId(), menuRuleId);
		WxMenuMatchRule wxMenuMatchRule = wxMenuMatchRuleSvc.load(WxMenuMatchRule.class, menuRuleId);
		mv.addObject("wxMenuItemLs", wxMenuItemLs);
		mv.addObject("wxMenuMatchRule", wxMenuMatchRule);
		mv.setViewName("wx/conditional-menu/wrap_wx_conditional_menu");
		return mv;
	}

	@RequestMapping(value = "/toWxDefaultMenuList.do")
	public ModelAndView toWxDefaultMenuList() {
		ModelAndView mv = new ModelAndView();
		Customer customer = customerSvc.findLoginCustomer();
		List<WxMenuItem> wxMenuItemLs = wxMenuSvc.wxMenuList(customer.getmSysId(), DEFAULT_MENU_RULE_ID);
		mv.addObject("wxMenuItemLs", wxMenuItemLs);
		mv.setViewName("wx/default-menu/wrap_wx_default_menu");
		return mv;
	}

	@RequestMapping(value = "/saveWxMenu")
	public @ResponseBody Result saveWxMenu(@RequestBody String wxMenuItemsJson) {
		try {
			Customer customer = customerSvc.findLoginCustomer();
			// wxMenuItemsJson = URLDecoder.decode(wxMenuItemsJson, "UTF-8");
			List<WxMenuItem> wxMenuItemLs = JSON.parseArray(wxMenuItemsJson, WxMenuItem.class);

			wxMenuSvc.saveWxMenu(customer.getmSysId(), wxMenuItemLs);

			this.createMenu(wxMenuItemLs.get(0).getMenuRuleId());

			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("saveWxMenu error...", e);
			return new Result(Const.NO, e.getMessage());
		}
	}
}
