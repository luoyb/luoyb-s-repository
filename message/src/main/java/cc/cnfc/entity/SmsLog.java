/**
 * 
 */
package cc.cnfc.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

/**
 * @author luoyb 短信发送日志
 *
 */
@Entity
@Table(name = "m_sms_log")
public class SmsLog extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1362694895159770562L;
	private String mSysId;
	private String msgId; // 短信的消息id
	private String mobile; // 发送手机号码
	private String content; // 发送的内容
	private String status; // 发送状态
	private String statusReason; // 状态形成的原因
	private Date sendTime; // 发送时间
	private String sendChannel; // 发送通道

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getmSysId() {
		return mSysId;
	}

	public void setmSysId(String mSysId) {
		this.mSysId = mSysId;
	}

	public String getSendChannel() {
		return sendChannel;
	}

	public void setSendChannel(String sendChannel) {
		this.sendChannel = sendChannel;
	}

}
