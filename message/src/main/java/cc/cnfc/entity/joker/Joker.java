package cc.cnfc.entity.joker;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

/**
 * 段子
 * 
 * @author luoyb
 *
 */
@Entity
@Table(name = "m_joker")
public class Joker extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1009775741521119021L;

	private String mSysId;
	private String content;
	private Date createTime;
	private Integer likeCount; // 点赞次数

	public String getmSysId() {
		return mSysId;
	}

	public void setmSysId(String mSysId) {
		this.mSysId = mSysId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

}
