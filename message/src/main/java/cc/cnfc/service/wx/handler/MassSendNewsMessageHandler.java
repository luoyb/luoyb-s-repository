package cc.cnfc.service.wx.handler;

import java.util.Map;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class MassSendNewsMessageHandler implements WxMpMessageHandler {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
			Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager, Map<String, Object> extra)
			throws WxErrorException {

		WxMpUserList userList = wxMpService.userList(null);

		WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
		massMessage.setMsgType(WxConsts.MASS_MSG_NEWS);
		massMessage.setMediaId(wxMessage.getMediaId());
		massMessage.getToUsers().addAll(userList.getOpenIds());

		WxMpMassSendResult massResult = wxMpService
				.massOpenIdsMessageSend(massMessage);

		logger.info(JSON.toJSONString(massResult));

		return null;
	}

}
