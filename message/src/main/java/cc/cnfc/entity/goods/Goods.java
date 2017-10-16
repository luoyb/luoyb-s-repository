package cc.cnfc.entity.goods;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

@Entity
@Table(name = "m_goods")
public class Goods extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1989987001017757280L;

	private String name; // 商品名称
	private String mainImageUrl;// 商品主图

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMainImageUrl() {
		return mainImageUrl;
	}

	public void setMainImageUrl(String mainImageUrl) {
		this.mainImageUrl = mainImageUrl;
	}

}
