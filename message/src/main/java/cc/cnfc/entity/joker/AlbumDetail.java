package cc.cnfc.entity.joker;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

/**
 * 相册详情
 * 
 * @author luoyb
 *
 */
@Entity
@Table(name = "m_album_detail")
public class AlbumDetail extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5828567476445935214L;

	private Long albumId;
	private String imageUrl;
	private String imageDesc;
	private Date createTime;

	public Long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getImageDesc() {
		return imageDesc;
	}

	public void setImageDesc(String imageDesc) {
		this.imageDesc = imageDesc;
	}

}
