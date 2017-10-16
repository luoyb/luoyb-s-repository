/**
 * 
 */
package cc.cnfc.dto.wx;

/**
 * @author luoyb
 *
 */
public class WxTextDto {

	private String openId;
	private String content;
	private String mobile; // 如果微信发送失败，有些方法会改用短信来发送

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
