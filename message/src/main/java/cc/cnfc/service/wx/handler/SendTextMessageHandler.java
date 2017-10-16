package cc.cnfc.service.wx.handler;

import java.util.Map;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.cnfc.dto.sms.SmsDto;
import cc.cnfc.pub.constant.Const.SmsSendChannel;
import cc.cnfc.service.sms.SmsService;

@Service
@Transactional
public class SendTextMessageHandler implements WxMpMessageHandler {

	@Autowired
	private SmsService smsSvc;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
			Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager, Map<String, Object> extra)
			throws WxErrorException {

		try {
			WxMpCustomMessage message = WxMpCustomMessage.TEXT()
					.toUser(wxMessage.getToUserName())
					.content(wxMessage.getContent()).build();
			wxMpService.customMessageSend(message);
		} catch (Exception e) {
			// 货款不足的微信信息，如果发送失败改用短信发送
			if (wxMessage.getContent().contains("货款不足")
					&& extra.get("mobile") != null) {
				SmsDto sDto = new SmsDto();
				sDto.setSendChannel(SmsSendChannel.NEXMO_STRICT.toString())
						.setMobile(extra.get("mobile").toString())
						.setSmsPrefix("好海淘联盟")
						.setContent(wxMessage.getContent());
				smsSvc.enqueueSms(extra.get("mSysId").toString(), sDto);
			} else {
				throw e;
			}
		}
		return null;
	}

}
