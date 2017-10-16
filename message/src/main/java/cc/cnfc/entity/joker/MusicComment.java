package cc.cnfc.entity.joker;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

/**
 * 乐评
 * 
 * @author luoyb
 *
 */
@Entity
@Table(name = "m_music_comment")
public class MusicComment extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5235400323745341140L;

	private Long musicId;
	private String comment; // 评论
	private Date createTime;
	private String author; // 评论者

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

	public Long getMusicId() {
		return musicId;
	}

	public void setMusicId(Long musicId) {
		this.musicId = musicId;
	}

}
