package cc.cnfc.entity.goods;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

@Entity
@Table(name = "m_combine_product_store_price")
public class CombineProductStorePrice extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4592971306094841216L;

	private Long combineProductId;
	private BigDecimal price;
	private Integer store;

	public Long getCombineProductId() {
		return combineProductId;
	}

	public void setCombineProductId(Long combineProductId) {
		this.combineProductId = combineProductId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

}
