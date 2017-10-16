/**
 * 
 */
package cc.cnfc.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

/**
 * @author luoyb 极光推送日志
 *
 */
@Entity
@Table(name = "m_jpush_log")
public class JpushLog extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1362694895159770562L;
	private String mSysId;
	private String pushMsg; // 推送内容
	private String respondMsg; // 响应内容
	private String status; // 发送状态
	private String statusReason; // 状态形成的原因
	private Date sendTime; // 发送时间

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(length = 2000)
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

	@Column(length = 2000)
	public String getPushMsg() {
		return pushMsg;
	}

	public void setPushMsg(String pushMsg) {
		this.pushMsg = pushMsg;
	}

	public String getRespondMsg() {
		return respondMsg;
	}

	public void setRespondMsg(String respondMsg) {
		this.respondMsg = respondMsg;
	}

}
