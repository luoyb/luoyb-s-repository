package cc.cnfc.controller.wx;

import java.util.List;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.mp.bean.WxMpGroup;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cc.cnfc.dto.MqDto;
import cc.cnfc.dto.Result;
import cc.cnfc.dto.wx.WxNewsDto;
import cc.cnfc.entity.ac.Customer;
import cc.cnfc.pub.constant.Const;
import cc.cnfc.pub.constant.Const.MqMsgType;
import cc.cnfc.service.CustomerService;
import cc.cnfc.service.MqService;
import cc.cnfc.service.wx.WxService;

import com.alibaba.fastjson.JSON;

/**
 * @author luoyb
 * @date Jun 8, 2016
 * @version $Revision$
 */
@Controller
@RequestMapping(value = "/wx")
public class WxController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustomerService customerSvc;
	@Autowired
	private WxService wxSvc;
	@Autowired
	private MqService mqSvc;

	@RequestMapping(value = "/toWxUserGroup.do")
	public ModelAndView toWxUserGroup() throws WxErrorException {
		ModelAndView mv = new ModelAndView();
		Customer customer = customerSvc.findLoginCustomer();
		List<WxMpGroup> wxGroupLs = wxSvc.getWxMpService(customer.getmSysId()).groupGet();
		mv.addObject("wxGroupLs", wxGroupLs);
		mv.setViewName("wx/wx-user-group/wrap_wx_user_group");
		return mv;
	}

	@RequestMapping(value = "/toSendGroupWx.do")
	public String toSendGroupWx() {
		return "wx/wrap_send_group_wx";
	}

	@RequestMapping(value = "/sendGroupWx.do")
	public @ResponseBody Result sendGroupWx(WxNewsDto nDto) {
		try {
			this.validate(nDto);
			WxMpXmlMessage msg = new WxMpXmlMessage();
			msg.setMsgType(Const.CUSTOM_MSG_GROUP_NEWS);
			msg.setUrl(nDto.getUrl());
			msg.setPicUrl(nDto.getPicUrl());
			msg.setTitle(nDto.getTitle());
			msg.setDescription(nDto.getDesc());
			// msg.setToUserName(nDto.getOpenId());

			Customer customer = customerSvc.findLoginCustomer();
			mqSvc.enqueue(new MqDto<WxMpXmlMessage>(customer.getmSysId(), MqMsgType.WX, msg));
			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("sendGroupWx error...", e);
			return new Result(Const.NO, e.getMessage());
		}
	}

	private void validate(WxNewsDto nDto) {
		if (StringUtils.isBlank(nDto.getTitle())) {
			throw new RuntimeException("发送微信失败，标题为空");
		}
		if (StringUtils.isBlank(nDto.getDesc())) {
			throw new RuntimeException("发送微信失败，内容为空");
		}
		if (StringUtils.isBlank(nDto.getUrl())) {
			throw new RuntimeException("发送微信失败，跳转链接为空");
		}
	}

	/**
	 * 通过授权页面获取我的信息
	 * 
	 * @param code
	 */
	@RequestMapping(value = "/findMyWxInfo.do")
	public String findMyWxInfo(String mSysId, String code, Model model) {
		try {
			if ("authdeny".equals(code)) {
				throw new Exception("用户未同意授权");
			}
			logger.info("mSysId：" + mSysId);
			WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxSvc.getWxMpService(mSysId).oauth2getAccessToken(code);
			WxMpUser wxMpUser = wxSvc.getWxMpService(mSysId).oauth2getUserInfo(wxMpOAuth2AccessToken, null);
			String userInfo = JSON.toJSONString(wxMpUser);
			logger.info(userInfo);
			model.addAttribute("user", wxMpUser);
		} catch (Exception e) {
			logger.error("findMyWxInfo error...", e);
		}
		return "wx/myWxInfo";
	}

}
