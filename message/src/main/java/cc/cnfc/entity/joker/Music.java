package cc.cnfc.entity.joker;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

/**
 * 电影
 * 
 * @author luoyb
 *
 */
@Entity
@Table(name = "m_music")
public class Music extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8703299548456599524L;

	private String coverUrl; // 封面路径
	private String name; // 音乐名字
	private Date createTime; // 创建时间
	private String musician; // 音乐家

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMusician() {
		return musician;
	}

	public void setMusician(String musician) {
		this.musician = musician;
	}

}
