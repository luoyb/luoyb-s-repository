package cc.cnfc.service.wx;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.cnfc.core.service.BaseService;
import cc.cnfc.entity.ac.Customer;
import cc.cnfc.pub.constant.Const;
import cc.cnfc.service.CustomerService;
import cc.cnfc.service.wx.handler.MassSendNewsMessageHandler;
import cc.cnfc.service.wx.handler.ReplyClickMenuHandler;
import cc.cnfc.service.wx.handler.ReplyMassSendJobFinishHandler;
import cc.cnfc.service.wx.handler.ReplyScanHandler;
import cc.cnfc.service.wx.handler.ReplySubscribeHandler;
import cc.cnfc.service.wx.handler.ReplyTextMessageHandler;
import cc.cnfc.service.wx.handler.ReplyUnsubscribeHandler;
import cc.cnfc.service.wx.handler.ReplyViewMenuHandler;
import cc.cnfc.service.wx.handler.SendGroupNewsMessageHandler;
import cc.cnfc.service.wx.handler.SendNewsMessageHandler;
import cc.cnfc.service.wx.handler.SendTextMessageHandler;
import cc.cnfc.util.Threads;

import com.beust.jcommander.internal.Maps;

@Service
@Transactional
public class WxService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8336701887551747722L;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustomerService customerInfoSvc;

	@Autowired
	private SendTextMessageHandler sendTextMessageHandler;
	@Autowired
	private ReplyTextMessageHandler replyTextMessageHandler;
	@Autowired
	private ReplyScanHandler replyScanHandler;
	@Autowired
	private ReplyClickMenuHandler replyClickMenuHandler;
	@Autowired
	private ReplyViewMenuHandler replyViewMenuHandler;
	@Autowired
	private ReplySubscribeHandler replySubscribeHandler;
	@Autowired
	private ReplyUnsubscribeHandler replyUnsubscribeHandler;
	@Autowired
	private ReplyMassSendJobFinishHandler replyMassSendJobFinishHandler;

	protected final Object createWxMpServiceLock = new Object();

	protected final Object createSendMsgRouterLock = new Object();

	protected final Object createReplyMsgRouterLock = new Object();

	/**
	 * mSysId->WxMpService的map
	 */
	private final ConcurrentHashMap<String, WxMpService> appId2WxMpService = new ConcurrentHashMap<String, WxMpService>();

	/**
	 * mSysId->发送消息路由器的map
	 */
	private final ConcurrentHashMap<String, WxMpMessageRouter> appId2SendMsgRouter = new ConcurrentHashMap<String, WxMpMessageRouter>();

	/**
	 * mSysId->被动回复消息路由器的map
	 */
	private final ConcurrentHashMap<String, WxMpMessageRouter> appId2ReplyMsgRouter = new ConcurrentHashMap<String, WxMpMessageRouter>();

	/**
	 * 获取wxMpService
	 * 
	 * @param appId
	 * @return
	 */
	public WxMpService getWxMpService(String mSysId) {
		if (!appId2WxMpService.containsKey(mSysId)) {
			synchronized (createWxMpServiceLock) {
				if (!appId2WxMpService.containsKey(mSysId)) {
					// 初始化wxMpService
					Customer customerInfo = customerInfoSvc.findByMSysId(mSysId);
					if (customerInfo == null) {
						throw new RuntimeException("未找到客户信息，appId：" + mSysId);
					}
					WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
					config.setAppId(customerInfo.getWxAppId());
					config.setSecret(customerInfo.getWxAppSecret());
					config.setToken(customerInfo.getWxToken());
					config.setAesKey(customerInfo.getWxAesKey());
					WxMpService wxMpService = new WxMpServiceImpl();
					wxMpService.setWxMpConfigStorage(config);
					appId2WxMpService.put(mSysId, wxMpService);
				}
			}
		}
		return appId2WxMpService.get(mSysId);

	}

	/**
	 * 获取发送消息路由器
	 * 
	 * @return
	 */
	public WxMpMessageRouter getSendMsgRoute(String mSysId) {
		if (!appId2SendMsgRouter.containsKey(mSysId)) {
			synchronized (createSendMsgRouterLock) {
				if (!appId2SendMsgRouter.containsKey(mSysId)) {
					WxMpMessageRouter router = this.createSendMsgRouter(mSysId);
					appId2SendMsgRouter.put(mSysId, router);
				}
			}
		}
		return appId2SendMsgRouter.get(mSysId);
	}

	/**
	 * 获取被动回复消息路由器
	 * 
	 * @return
	 */
	public WxMpMessageRouter getReplyMsgRoute(String mSysId) {
		if (!appId2ReplyMsgRouter.containsKey(mSysId)) {
			synchronized (createReplyMsgRouterLock) {
				if (!appId2ReplyMsgRouter.containsKey(mSysId)) {
					WxMpMessageRouter router = this.createReplyMsgRouter(mSysId);
					appId2ReplyMsgRouter.put(mSysId, router);
				}
			}
		}
		return appId2ReplyMsgRouter.get(mSysId);
	}

	/**
	 * 创建发送消息路由器
	 * 
	 * @param appId
	 * @return
	 */
	private WxMpMessageRouter createSendMsgRouter(String mSysId) {
		// 发送消息路由
		WxMpMessageRouter sendMsgRouter = new WxMpMessageRouter(this.getWxMpService(mSysId));
		sendMsgRouter
		// 发送文本消息
				.rule().async(false).msgType(WxConsts.CUSTOM_MSG_TEXT).handler(sendTextMessageHandler).end()
				// 发送图文消息
				.rule().msgType(WxConsts.CUSTOM_MSG_NEWS).handler(new SendNewsMessageHandler()).end()
				// 用客服图文发送接口发送群发消息，不是活动的用户都会发送失败
				.rule().msgType(Const.CUSTOM_MSG_GROUP_NEWS).handler(new SendGroupNewsMessageHandler()).end()
				// 群发图文消息
				.rule().msgType(WxConsts.MASS_MSG_NEWS).handler(new MassSendNewsMessageHandler()).end();
		return sendMsgRouter;
	}

	/**
	 * 创建被动回复消息路由器
	 * 
	 * @param appId
	 * @return
	 */
	private WxMpMessageRouter createReplyMsgRouter(String mSysId) {
		// 被动回复消息路由
		WxMpMessageRouter replyMsgRouter = new WxMpMessageRouter(this.getWxMpService(mSysId));
		replyMsgRouter
				// 同步回复文本消息
				.rule()
				.async(false)
				.msgType(WxConsts.XML_MSG_TEXT)
				.handler(replyTextMessageHandler)
				.end()
				// // 同步回复扫描事件（暂时由其他平台自己实现）
				// .rule()
				// .async(false)
				// .msgType(WxConsts.XML_MSG_EVENT)
				// .event(WxConsts.EVT_SCAN)
				// .handler(replyScanHandler)
				// .end()
				// 同步回复点击菜单拉取消息时事件
				.rule()
				.async(false)
				.msgType(WxConsts.XML_MSG_EVENT)
				.event(WxConsts.EVT_CLICK)
				.handler(replyClickMenuHandler)
				.end()
				// 同步回复回点击菜单跳转链接时的事件推送
				.rule()
				.async(false)
				.msgType(WxConsts.XML_MSG_EVENT)
				.event(WxConsts.EVT_VIEW)
				.handler(replyViewMenuHandler)
				.end()
				// // 同步回复订阅事件（暂时由其他平台自己实现）
				// .rule().async(false).msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_SUBSCRIBE)
				// .handler(replySubscribeHandler)
				// .end()
				// 同步回复取消订阅事件
				.rule().async(false).msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_UNSUBSCRIBE)
				.handler(replyUnsubscribeHandler).end()
				// 异步记录群发结果事件
				.rule().async(true).msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_MASS_SEND_JOB_FINISH)
				.handler(replyMassSendJobFinishHandler).end();
		return replyMsgRouter;
	}

	public String checkSignature(String mSysId, String signature, String timestamp, String nonce, String echostr) {
		WxMpService wxMpSvc = getWxMpService(mSysId);
		boolean isSuccess = wxMpSvc.checkSignature(timestamp, nonce, signature);
		if (isSuccess) {
			logger.info("checkSignature success.");
			return echostr;
		} else {
			throw new RuntimeException("checkSignature fail");
		}
	}

	public String checkSignature(String signature, String timestamp, String nonce, String echostr) {
		List<Customer> ciLs = customerInfoSvc.findAll();
		for (Customer ci : ciLs) {
			try {
				return this.checkSignature(ci.getmSysId(), signature, timestamp, nonce, echostr);
			} catch (Exception e) {
				continue;
			}
		}
		throw new RuntimeException("checkSignature fail");
	}

	/**
	 * 处理从mq来的消息，route方法里面有重复消息判断
	 * 
	 * @param mSysId
	 * @param wxMpXmlMessage
	 */
	public void handlerWxMsg(String mSysId, WxMpXmlMessage wxMpXmlMessage, Map<String, Object> extra) {
		// 确保消息不会被当作重复消息
		Threads.randomSleep();
		wxMpXmlMessage.setCreateTime(System.currentTimeMillis());
		WxMpMessageRouter router = getSendMsgRoute(mSysId);
		if (extra == null) {
			extra = Maps.newHashMap();
		}
		extra.put("mSysId", mSysId);
		router.route(wxMpXmlMessage, extra);
	}
}
