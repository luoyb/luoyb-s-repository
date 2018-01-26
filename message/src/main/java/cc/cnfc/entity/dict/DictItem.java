package cc.cnfc.entity.dict;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

/**
 * 字典项表
 * 
 * @author luoyb
 *
 */
@Entity
@Table(name = "m_dict_item")
public class DictItem extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8912520769687269584L;

	private String item_key; // 字典项的key
	private String item_desc;// 字典项的描述
	private Integer state; // 1-启用，0-禁用
	private Integer sort; // 排序，0，1，2，3 ...
	private String dict_id; // 所属字典的ID

	public String getItem_key() {
		return item_key;
	}

	public void setItem_key(String item_key) {
		this.item_key = item_key;
	}

	public String getItem_desc() {
		return item_desc;
	}

	public void setItem_desc(String item_desc) {
		this.item_desc = item_desc;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getDict_id() {
		return dict_id;
	}

	public void setDict_id(String dict_id) {
		this.dict_id = dict_id;
	}

}
