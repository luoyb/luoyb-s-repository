/**
 * 
 */
package cc.cnfc.dto.jpush;

import java.util.Map;
import java.util.Set;

import cc.cnfc.pub.constant.Const.JushAudienceType;

/**
 * 极光推送数据
 * 
 * @author luoyb
 *
 */
public class JpushDto {

	private Set<String> alias; // 别名
	private String title;// 标题
	private String alert;// 通知内容
	private Map<String, String> extras;// 附加数据
	private JushAudienceType jpushAudienceType;

	public Set<String> getAlias() {
		return alias;
	}

	public JpushDto setAlias(Set<String> alias) {
		this.alias = alias;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public JpushDto setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getAlert() {
		return alert;
	}

	public JpushDto setAlert(String alert) {
		this.alert = alert;
		return this;
	}

	public Map<String, String> getExtras() {
		return extras;
	}

	public JpushDto setExtras(Map<String, String> extras) {
		this.extras = extras;
		return this;
	}

	public JushAudienceType getJpushAudienceType() {
		return jpushAudienceType;
	}

	public JpushDto setJpushAudienceType(JushAudienceType jpushAudienceType) {
		this.jpushAudienceType = jpushAudienceType;
		return this;
	}

}
