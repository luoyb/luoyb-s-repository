package cc.cnfc.entity.goods;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

@Entity
@Table(name = "m_product")
public class Product extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4592971306094841216L;

	private Long goodsId;
	private String name;

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
