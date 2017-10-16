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
@Table(name = "m_film")
public class Film extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 431354590612275796L;

	private String coverUrl; // 封面路径
	private String name; // 电影名称
	private Date createTime; // 创建时间
	private String director; // 导演

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

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

}
