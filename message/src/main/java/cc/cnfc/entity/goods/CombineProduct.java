package cc.cnfc.entity.goods;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

@Entity
@Table(name = "m_combine_product")
public class CombineProduct extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4592971306094841216L;

	private String productIds;

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

}
