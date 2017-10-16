package cc.cnfc.service.wx.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReplySubscribeHandler extends WrapWxMpMessageHandler {

	// @Override
	// public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
	// Map<String, Object> context, WxMpService wxMpService,
	// WxSessionManager sessionManager, Map<String, Object> extra)
	// throws WxErrorException {
	//
	// String replyInfo = "欢迎";
	// String eventKey = wxMessage.getEventKey();
	// if (StringUtils.isNotBlank(eventKey)) {
	// String[] strArr = eventKey.split("_");
	// if ("qrscene".equals(strArr[0])) {
	// replyInfo += "从场景值为" + strArr[1] + "的二维码";
	// }
	// }
	// replyInfo += "订阅本公众号";
	//
	// WxMpXmlOutMessage xmlOutMsg = WxMpXmlOutMessage.TEXT()
	// .content(replyInfo).fromUser(wxMessage.getToUserName())
	// .toUser(wxMessage.getFromUserName()).build();
	// return xmlOutMsg;
	//
	// }

}
