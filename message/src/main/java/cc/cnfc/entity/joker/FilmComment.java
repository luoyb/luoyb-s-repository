package cc.cnfc.entity.joker;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

/**
 * 影评
 * 
 * @author luoyb
 *
 */
@Entity
@Table(name = "m_film_comment")
public class FilmComment extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 663156904630357477L;

	private Long filmId;
	private String comment; // 评论
	private Date createTime;
	private String author; // 评论者

	public Long getFilmId() {
		return filmId;
	}

	public void setFilmId(Long filmId) {
		this.filmId = filmId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
