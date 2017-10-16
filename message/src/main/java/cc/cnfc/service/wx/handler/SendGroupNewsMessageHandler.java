package cc.cnfc.service.wx.handler;

import java.util.List;
import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendGroupNewsMessageHandler implements WxMpMessageHandler {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
			Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager, Map<String, Object> extra)
			throws WxErrorException {

		List<String> userList = wxMpService.userList(null).getOpenIds();
		for (String openId : userList) {
			try {
				WxMpCustomMessage.WxArticle article = new WxMpCustomMessage.WxArticle();
				article.setUrl(wxMessage.getUrl());
				article.setPicUrl(wxMessage.getPicUrl());
				article.setDescription(wxMessage.getDescription());
				article.setTitle(wxMessage.getTitle());

				WxMpCustomMessage msg = WxMpCustomMessage.NEWS().toUser(openId)
						.addArticle(article).build();
				wxMpService.customMessageSend(msg);
				logger.info("push success , openId : " + openId);
			} catch (Exception e) {
				logger.info("push fail , openId : " + openId);
			}
		}

		return null;
	}

}
