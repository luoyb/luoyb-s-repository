package cc.cnfc.pub.constant;

public class Const {

	// 短信占位符
	public final static String SMS_PLACEHOLDER = "#\\$\\{.*?\\}";

	// 每页的记录数
	public final static int PAGE_SIZE = 10;

	// 一次发送的最多手机号码个数
	public final static int SMS_MOBILE_MAX_NUM = 50;

	public final static String YES = "1";

	public final static String NO = "0";

	public final static String LOGIN_CUSTOMER = "loginCustomer";

	// /////////////////////
	// 微信消息类型补充
	// /////////////////////
	public static final String CUSTOM_MSG_GROUP_NEWS = "groupnews";

	/**
	 * 消息队列的消息类型
	 */
	public enum MqMsgType {
		SMS, WX, JPUSH
	}

	/**
	 * 手机号码来源，用户自定义，excel导入，第三方系统注册用户
	 */
	public enum MobileSource {
		CUSTOM, EXCEL, REGISTER
	}

	/**
	 * 短信的发送状态，投递失败，投递成功，发送失败，发送成功
	 */
	public enum SmsSendStatus {
		DELIVER_FAIL, DELIVER_SUCCESS, SEND_FAIL, SEND_SUCCESS
	}

	/**
	 * 极光推送类型，群发，别名发送
	 */
	public enum JushAudienceType {
		ALL, ALIAS
	}

	/**
	 * 短信发送通过，nexmo营销通道，nexmo严格通道，CHONRY，yc易驰科技,YC_MARKETING-易驰营销通道
	 * 
	 */
	public enum SmsSendChannel {
		NEXMO_MARKETING, NEXMO_STRICT, CHONRY, YC, YC_MARKETING
	}

	/**
	 * 
	 * 短信类型，biz-业务短信，marketing-营销短信
	 *
	 */
	public enum SmsType {
		BIZ, MARKETING
	}

	/**
	 * 短信模版审核状态
	 * 
	 */
	public enum SmsTemplateStatus {
		APPLYING, PASS, REFUSE
	}

	// 微信菜单等级
	public static String WX_MENU_LEVEL_1 = "1";
	public static String WX_MENU_LEVEL_2 = "2";

}
