/**
 * 
 */
package cc.cnfc.dto.sms;

import java.util.Map;

import cc.cnfc.pub.constant.Const;

/**
 * @author luoyb
 *
 */
public class SmsDto {

	private String sendChannel; // 发送通道
	private boolean isRealTimeSend = true; // 是否实时发送
	private String mobile;
	private String content;
	private String isGroupSend = Const.NO; // 是否群发
	private String smsPrefix; // 短信前缀，为空则用账户配置的前缀
	private String smsType; // BIZ-业务短信，MARKETING-营销短信
	private Object[] datas; // 短信数据，用于填充模版
	private Map<String, ?> dataMap; // 短信数据，用于填充模板
	private String templateKey; // 短信模版KEY

	public String getMobile() {
		return mobile;
	}

	public SmsDto setMobile(String mobile) {
		this.mobile = mobile;
		return this;
	}

	public String getContent() {
		return content;
	}

	public SmsDto setContent(String content) {
		this.content = content;
		return this;
	}

	public String getSendChannel() {
		return sendChannel;
	}

	public SmsDto setSendChannel(String sendChannel) {
		this.sendChannel = sendChannel;
		return this;
	}

	public boolean isRealTimeSend() {
		return isRealTimeSend;
	}

	public void setRealTimeSend(boolean isRealTimeSend) {
		this.isRealTimeSend = isRealTimeSend;
	}

	public String getIsGroupSend() {
		return isGroupSend;
	}

	// 一次性提交的短信数量是否大于1
	public String isMoreThan1() {
		if (this.getMobile().split(",|，").length > 1) {
			return Const.YES;
		}
		return Const.NO;
	}

	public void setIsGroupSend(String isGroupSend) {
		this.isGroupSend = isGroupSend;
	}

	public String getSmsPrefix() {
		return smsPrefix;
	}

	public SmsDto setSmsPrefix(String smsPrefix) {
		this.smsPrefix = smsPrefix;
		return this;
	}

	public String getSmsType() {
		return smsType;
	}

	public SmsDto setSmsType(String smsType) {
		this.smsType = smsType;
		return this;
	}

	public Object[] getDatas() {
		return datas;
	}

	public void setDatas(Object[] datas) {
		this.datas = datas;
	}

	public String getTemplateKey() {
		return templateKey;
	}

	public void setTemplateKey(String templateKey) {
		this.templateKey = templateKey;
	}

	public Map<String, ?> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, ?> dataMap) {
		this.dataMap = dataMap;
	}

}
