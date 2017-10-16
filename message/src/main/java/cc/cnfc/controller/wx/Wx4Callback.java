package cc.cnfc.controller.wx;

import java.util.Map;

import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import cc.cnfc.entity.ac.Customer;
import cc.cnfc.service.CustomerService;
import cc.cnfc.service.wx.WxService;

import com.beust.jcommander.internal.Maps;

@Controller
@RequestMapping(value = "/wx4cb")
public class Wx4Callback {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WxService wxSvc;
	@Autowired
	private CustomerService customerInfoSvc;
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "/callback.do", method = RequestMethod.POST)
	public @ResponseBody String callback(String signature, String timestamp, String nonce, String echostr,
			@RequestBody String xmlMsg) {
		try {
			logger.info(xmlMsg);
			WxMpXmlMessage wxMpXmlMsg = WxMpXmlMessage.fromXml(xmlMsg);

			Customer customerInfo = customerInfoSvc.findByWxSerialNum(wxMpXmlMsg.getToUserName());

			if (customerInfo == null) {
				throw new RuntimeException("未找到customer info，wxSerialNum：" + wxMpXmlMsg.getToUserName());
			}

			wxSvc.checkSignature(customerInfo.getmSysId(), signature, timestamp, nonce, echostr);

			Map<String, Object> extra = Maps.newHashMap();
			extra.put("mSysId", customerInfo.getmSysId());
			WxMpXmlOutMessage xmlOutMsg = wxSvc.getReplyMsgRoute(customerInfo.getmSysId()).route(wxMpXmlMsg, extra);
			// 如果本平台处理的不了的微信推送，则转给其他各平台自己处理
			if (xmlOutMsg == null) {
				if (StringUtils.isNotBlank(customerInfo.getmCallBackUrl())) {
					logger.info("该推送本平台处理不了，转给其他各平台自己处理");
					String url = customerInfo.getmCallBackUrl()
							+ "?signature={signature}&timestamp={timestamp}&nonce={nonce}&echostr={echostr}";
					logger.info("post url : " + url);
					String resp = restTemplate.postForObject(url, xmlMsg, String.class, signature, timestamp, nonce,
							echostr);
					logger.info("response : " + resp);
					return resp;
				} else {
					logger.info("该推送没法响应");
					return null;
				}
			}
			logger.info(xmlOutMsg.toXml());
			return xmlOutMsg.toXml();
		} catch (Exception e) {
			logger.error("callback error", e);
			return StringUtils.EMPTY;
		}
	}

	@RequestMapping(value = "/callback.do", method = RequestMethod.GET)
	public @ResponseBody String callback(String signature, String timestamp, String nonce, String echostr) {
		try {
			return wxSvc.checkSignature(signature, timestamp, nonce, echostr);
		} catch (Exception e) {
			logger.error("callback error", e);
			return StringUtils.EMPTY;
		}
	}

}
