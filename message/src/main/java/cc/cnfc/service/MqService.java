/**
 * 
 */
package cc.cnfc.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import me.chanjar.weixin.mp.bean.WxMpXmlMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.cnfc.dto.MqDto;
import cc.cnfc.dto.jpush.JpushDto;
import cc.cnfc.dto.sms.SmsDto;
import cc.cnfc.pub.constant.Const.MqMsgType;
import cc.cnfc.service.jpush.JpushService;
import cc.cnfc.service.sms.SmsService;
import cc.cnfc.service.wx.WxService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * @author luoyb 消息队列服务类
 */
@Service
@Transactional
public class MqService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private SmsService smsSvc;
	@Autowired
	private WxService wxSvc;
	@Autowired
	private JpushService jpushSvc;

	/**
	 * 入队列
	 */
	public void enqueue(final MqDto<?> dto) {
		final String mqDtoJson = JSON.toJSONString(dto);
		this.enqueue(mqDtoJson);
	}

	/**
	 * 入队列
	 */
	public void enqueue(final String mqDtoJson) {
		logger.info("enqueue：" + mqDtoJson);
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(mqDtoJson);
			}
		});
		// this.dequque(mqDtoJson);
	}

	/**
	 * 出队列
	 */
	public void dequque(String mqDtoJson) {
		try {
			logger.info("dequque：" + mqDtoJson);
			MqDto<?> mqDto = JSON.parseObject(mqDtoJson, new TypeReference<MqDto<?>>() {
			});

			MqMsgType mqMsgType = mqDto.getMsgType();

			switch (mqMsgType) {
			// 短信消息
			case SMS:
				MqDto<SmsDto> smsMqDto = JSON.parseObject(mqDtoJson, new TypeReference<MqDto<SmsDto>>() {
				});
				smsSvc.handlerSms(mqDtoJson, smsMqDto.getmSysId(), smsMqDto.getMsgObject());
				break;
			// 微信消息
			case WX:
				MqDto<WxMpXmlMessage> wxMqDto = JSON.parseObject(mqDtoJson, new TypeReference<MqDto<WxMpXmlMessage>>() {
				});
				wxSvc.handlerWxMsg(wxMqDto.getmSysId(), wxMqDto.getMsgObject(), wxMqDto.getExtra());
				break;
			// 极光推送
			case JPUSH:
				MqDto<JpushDto> jpushMqDto = JSON.parseObject(mqDtoJson, new TypeReference<MqDto<JpushDto>>() {
				});
				jpushSvc.handlerJpush(jpushMqDto.getmSysId(), jpushMqDto.getMsgObject());
			default:
				break;
			}

		} catch (Exception e) {
			logger.error("dequque error...", e);
		}
	}

}
