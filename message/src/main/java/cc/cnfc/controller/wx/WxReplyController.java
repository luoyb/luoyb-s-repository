package cc.cnfc.controller.wx;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cc.cnfc.core.orm.Page;
import cc.cnfc.dto.Result;
import cc.cnfc.entity.WxReplyMsg;
import cc.cnfc.entity.ac.Customer;
import cc.cnfc.pub.constant.Const;
import cc.cnfc.service.CustomerService;
import cc.cnfc.service.wx.WxReplyMsgService;

/**
 * @author luoyb
 * @date Jun 8, 2016
 * @version $Revision$
 */
@Controller
@RequestMapping(value = "/wxReply")
public class WxReplyController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WxReplyMsgService wxReplyMsgSvc;
	@Autowired
	private CustomerService customerSvc;

	@RequestMapping(value = "/textKeywordReplyOper.do")
	public @ResponseBody void textKeywordReplyOper(String id, String name, String keyWord, String replyType,
			String content, String oper) throws Exception {
		Customer customer = customerSvc.findLoginCustomer();
		if ("add".equals(oper)) {
			wxReplyMsgSvc.saveTextKeyWordReply(customer.getmSysId(), name, keyWord, replyType, content);
		}
		if ("edit".equals(oper)) {
			wxReplyMsgSvc.updateTextKeyWordReply(Long.valueOf(id), customer.getmSysId(), name, keyWord, replyType,
					content);
		}
		if ("del".equals(oper)) {
			String[] idArr = id.split(",");
			for (String _id : idArr) {
				if (StringUtils.isNotBlank(_id)) {
					WxReplyMsg replyMsg = wxReplyMsgSvc.load(WxReplyMsg.class, Long.valueOf(_id));
					wxReplyMsgSvc.delete(replyMsg);
				}
			}
		}
	}

	@RequestMapping(value = "/toTextKeywordReplyList.do")
	public String toTextKeywordReplyList() {
		return "wx/text-keyword-reply/wrap_text_keyword_reply_list";
	}

	@RequestMapping(value = "/textKeywordReplyList.do")
	public @ResponseBody String textKeywordReplyList() {
		try {
			Customer customer = customerSvc.findLoginCustomer();
			Page page = wxReplyMsgSvc.textKeywordReplyList(customer.getmSysId());
			return page.getJQGridJsonString();
		} catch (Exception e) {
			logger.error("wxReply.textKeywordReplyList error...", e);
			return null;
		}
	}

	@RequestMapping(value = "/toTextDefaultReply.do")
	public ModelAndView toTextDefaultReply() {
		ModelAndView mv = new ModelAndView();
		WxReplyMsg replyMsg = this.findUniqueReply(WxConsts.XML_MSG_TEXT, WxReplyMsgService.DEFAULT_KEY_WORD);
		mv.addObject("replyMsg", replyMsg);
		mv.setViewName("wx/text-default-reply/wrap_text_default_reply");
		return mv;
	}

	@RequestMapping(value = "/saveTextDefaultReply")
	public @ResponseBody Result saveTextDefaultReply(WxReplyMsg replyMsg) {
		try {
			Customer customer = customerSvc.findLoginCustomer();
			if (StringUtils.isBlank(replyMsg.getContent())) {
				throw new RuntimeException("请输入回复内容！");
			}
			wxReplyMsgSvc.saveTextDefaultReply(customer.getmSysId(), replyMsg);
			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("saveTextDefaultReply error...", e);
			return new Result(Const.NO, e.getMessage());
		}
	}

	@RequestMapping(value = "/toSubscribeReply.do")
	public ModelAndView toSubscribeReply() {
		ModelAndView mv = new ModelAndView();
		WxReplyMsg replyMsg = this.findUniqueReply(WxConsts.EVT_SUBSCRIBE, null);
		mv.addObject("replyMsg", replyMsg);
		mv.setViewName("wx/subscribe-reply/wrap_subscribe_reply");
		return mv;
	}

	@RequestMapping(value = "/saveSubscribeReply")
	public @ResponseBody Result saveSubscribeReply(WxReplyMsg replyMsg) {
		try {
			Customer customer = customerSvc.findLoginCustomer();
			if (StringUtils.isBlank(replyMsg.getContent())) {
				throw new RuntimeException("请输入回复内容！");
			}
			wxReplyMsgSvc.saveSubscribeReply(customer.getmSysId(), replyMsg);
			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("saveSubscribeReply error...", e);
			return new Result(Const.NO, e.getMessage());
		}
	}

	@RequestMapping(value = "/findUniqueReply")
	public @ResponseBody WxReplyMsg findUniqueReply(String msgType, String keyWord) {
		try {
			Customer customer = customerSvc.findLoginCustomer();
			WxReplyMsg replyMsg = wxReplyMsgSvc.findUniqueReply(customer.getmSysId(), msgType, keyWord);
			return replyMsg;
		} catch (Exception e) {
			logger.error("findUniqueReply error...", e);
			return null;
		}
	}
}
