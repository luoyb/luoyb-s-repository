package cc.cnfc.entity.joker;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

/**
 * 相册
 * 
 * @author luoyb
 *
 */
@Entity
@Table(name = "m_album")
public class Album extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 431354590612275796L;

	private String coverUrl; // 封面路径
	private String name; // 相册名称
	private Date createTime; // 创建时间
	private String author;// 作者

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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
