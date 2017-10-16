package cc.cnfc.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

/**
 * 用户短信模版
 * 
 * 唯一：mSysId - templateKey - status
 * 
 * @author luoyb
 *
 */
@Entity
@Table(name = "m_sms_template")
public class SmsTemplate extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4369178846163313381L;
	private String mSysId;
	private String templateKey; // 模版key
	private String templateName; // 模板名称
	private String templateCnt; // 短信模版
	private String status; // 模版审核状态
	private Date createTime; // 创建时间
	private Date updateTime; // 修改时间
	private boolean isLatest; // 是否是最近的一条

	public String getmSysId() {
		return mSysId;
	}

	public void setmSysId(String mSysId) {
		this.mSysId = mSysId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTemplateKey() {
		return templateKey;
	}

	public void setTemplateKey(String templateKey) {
		this.templateKey = templateKey;
	}

	public String getTemplateCnt() {
		return templateCnt;
	}

	public void setTemplateCnt(String templateCnt) {
		this.templateCnt = templateCnt;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public boolean isLatest() {
		return isLatest;
	}

	public void setLatest(boolean isLatest) {
		this.isLatest = isLatest;
	}

}
