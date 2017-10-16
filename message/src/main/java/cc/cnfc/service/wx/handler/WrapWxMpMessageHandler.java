package cc.cnfc.service.wx.handler;

import java.util.Map;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.WxMpMaterialNews.WxMpMaterialNewsArticle;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.outxmlbuilder.NewsBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.cnfc.entity.WxReplyMsg;
import cc.cnfc.service.wx.WxReplyMsgService;

@Service
@Transactional
public class WrapWxMpMessageHandler implements WxMpMessageHandler {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WxReplyMsgService wxReplyMsgSvc;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager, Map<String, Object> extra) throws WxErrorException {
		WxReplyMsg wxReplyMsg = this.getWxReplyMsg(wxMessage, extra);
		if (wxReplyMsg == null) {
			return null;
		}
		return this.getWxMpXmlOutMessage(wxReplyMsg, wxMessage, wxMpService);
	}

	public WxReplyMsg getWxReplyMsg(WxMpXmlMessage wxMessage, Map<String, Object> extra) {
		String mSysId = extra.get("mSysId").toString();
		if (WxConsts.XML_MSG_TEXT.equals(wxMessage.getMsgType())) {
			String keyWord = wxMessage.getContent();
			WxReplyMsg wxReplyMsg = wxReplyMsgSvc.findUniqueReply(mSysId, wxMessage.getMsgType(), keyWord);
			if (wxReplyMsg == null) {
				wxReplyMsg = wxReplyMsgSvc.findDefaultReply(mSysId, wxMessage.getMsgType());
			}
			return wxReplyMsg;
		}
		if (WxConsts.XML_MSG_EVENT.equals(wxMessage.getMsgType())) {
			String keyWord = wxMessage.getEventKey();
			WxReplyMsg wxReplyMsg = wxReplyMsgSvc.findUniqueReply(mSysId, wxMessage.getEvent(), keyWord);
			if (wxReplyMsg == null) {
				wxReplyMsg = wxReplyMsgSvc.findDefaultReply(mSysId, wxMessage.getEvent());
			}
			return wxReplyMsg;
		}
		return null;
	}

	public WxMpXmlOutMessage getWxMpXmlOutMessage(WxReplyMsg wxReplyMsg, WxMpXmlMessage wxMessage,
			WxMpService wxMpService) throws WxErrorException {
		WxMpXmlOutMessage xmlOutMsg = null;
		switch (wxReplyMsg.getReplyType()) {
		case WxConsts.XML_MSG_TEXT:
			xmlOutMsg = WxMpXmlOutMessage.TEXT().content(wxReplyMsg.getContent()).fromUser(wxMessage.getToUserName())
					.toUser(wxMessage.getFromUserName()).build();
			break;
		case WxConsts.XML_MSG_IMAGE:
			xmlOutMsg = WxMpXmlOutMessage.IMAGE().mediaId(wxReplyMsg.getContent()).fromUser(wxMessage.getToUserName())
					.toUser(wxMessage.getFromUserName()).build();
			break;
		case WxConsts.XML_MSG_VOICE:
			xmlOutMsg = WxMpXmlOutMessage.VOICE().mediaId(wxReplyMsg.getContent()).fromUser(wxMessage.getToUserName())
					.toUser(wxMessage.getFromUserName()).build();
			break;
		case WxConsts.XML_MSG_VIDEO:
			xmlOutMsg = WxMpXmlOutMessage.VIDEO().mediaId(wxReplyMsg.getContent()).fromUser(wxMessage.getToUserName())
					.toUser(wxMessage.getFromUserName()).build();
			break;
		// case WxConsts.XML_MSG_MUSIC:
		// xmlOutMsg = WxMpXmlOutMessage.MUSIC().title(wxReplyMsg.getTitle()).description(wxReplyMsg.getDescription())
		// .musicUrl(wxReplyMsg.getMusicURL()).hqMusicUrl(wxReplyMsg.gethQMusicUrl())
		// .thumbMediaId(wxReplyMsg.getThumbMediaId()).fromUser(wxMessage.getToUserName())
		// .toUser(wxMessage.getFromUserName()).build();
		// break;
		case WxConsts.XML_MSG_NEWS:
			NewsBuilder newsBuilder = WxMpXmlOutMessage.NEWS();
			WxMpMaterialNews news = wxMpService.materialNewsInfo(wxReplyMsg.getContent());
			for (WxMpMaterialNewsArticle article : news.getArticles()) {
				WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
				item.setTitle(article.getTitle());
				item.setDescription(article.getDigest());
				item.setPicUrl(article.getThumbUrl());
				item.setUrl(article.getUrl());
				newsBuilder.addArticle(item);
			}
			xmlOutMsg = newsBuilder.fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
			break;
		}
		return xmlOutMsg;
	}
}
