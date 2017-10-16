package cc.cnfc.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

/**
 * 临时存放不马上发送的短信信息
 * 
 * @author luoyb
 *
 */
@Entity
@Table(name = "m_temp_sms")
public class TempSms extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2709789299397638723L;

	private String mqDtoJson;
	private Date createTime;

	public String getMqDtoJson() {
		return mqDtoJson;
	}

	public void setMqDtoJson(String mqDtoJson) {
		this.mqDtoJson = mqDtoJson;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
