package cc.cnfc.service.wx.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReplyMassSendJobFinishHandler extends WrapWxMpMessageHandler {

	// @Override
	// public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
	// WxSessionManager sessionManager, Map<String, Object> extra) throws WxErrorException {
	//
	// logger.info(JSON.toJSONString(wxMessage));
	//
	// if (WxConsts.MASS_ST_SUCCESS.equals(wxMessage.getStatus())) {
	// logger.info("mass send success");
	// } else {
	// logger.info("mass send fail");
	// }
	// return null;
	// }
}
