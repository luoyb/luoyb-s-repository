package cc.cnfc.entity.dict;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

/**
 * 字典表
 * 
 * @author luoyb
 *
 */
@Entity
@Table(name = "m_dict")
public class Dict extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6803526360407404993L;

	private String dict_key; // 字典的key
	private String dict_desc;// 字典key的描述
	private Integer state; // 1-启用，0-禁用

	public String getDict_key() {
		return dict_key;
	}

	public void setDict_key(String dict_key) {
		this.dict_key = dict_key;
	}

	public String getDict_desc() {
		return dict_desc;
	}

	public void setDict_desc(String dict_desc) {
		this.dict_desc = dict_desc;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
