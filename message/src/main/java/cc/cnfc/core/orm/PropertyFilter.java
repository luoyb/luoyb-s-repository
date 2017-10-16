package cc.cnfc.core.orm;

import java.util.Date;

import cc.cnfc.core.utils.ReflectionUtil;

/**
 * 与具体ORM实现无关的属性过滤条件封装类. PropertyFilter主要记录页面中简单的搜索过滤条件
 * 前台控件必须满足如下命名规范:q_属性名_操作符_比较类型 eg. q_loginName_like_s
 * 
 * @author denny
 * @date 2010-1-4
 * @version 1.0
 */
public class PropertyFilter {
	public enum MatchType {
		EQ("="), LIKE("%value%"), LLIKE("%value"), RLIKE("value%"), LT("<"), GT(
				">"), LE("<="), GE(">="), NE("<>"), IN("IN"), ISNULL("isnull"), ISNOTNULL(
				"isnotnull");
		private String operate;

		MatchType(String operate) {
			this.operate = operate;
		}

		public String getValue() {
			return operate;
		}
	}

	/**
	 * 属性数据类型.
	 */
	public enum PropertyType {
		S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(
				Date.class), B(Boolean.class);

		private Class<?> clazz;

		PropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getValue() {
			return clazz;
		}
	}

	private String propertyName = null;
	private Class<?> propertyType = null;
	private Object propertyValue = null;
	private MatchType matchType = MatchType.EQ;

	public PropertyFilter() {
	}

	/**
	 * @param filterName
	 *            比较属性字符串,含待比较的比较类型、属性值类型及属性列表. eg. q_loginName_like_s
	 * @param value
	 *            待比较的值.
	 */
	public PropertyFilter(final String filterName, final Object value) {

		String[] params = filterName.split("_");
		if (params.length != 4 || !params[0].toUpperCase().equals("Q")) {
			return;
		}
		String matchTypeCode = params[2].toUpperCase();
		String propertyTypeCode = params[3].toUpperCase();
		try {
			matchType = Enum.valueOf(MatchType.class, matchTypeCode);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("控件没有按规则命名,无法得到属性比较类型.", e);
		}

		try {
			propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode)
					.getValue();
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("控件没有按规则命名,无法得到属性值类型.", e);
		}

		propertyName = params[1];

		// 按entity property中的类型将字符串转化为实际类型.
		this.propertyValue = ReflectionUtil.convertValue(value, propertyType);
	}

	/**
	 * 获取比较属性名称列表.
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * 获取比较值.
	 */
	public Object getPropertyValue() {
		return propertyValue;
	}

	/**
	 * 获取比较值.
	 */
	public void setPropertyValue(Object obj) {
		this.propertyValue = obj;
	}

	/**
	 * 获取比较值的类型.
	 */
	public Class<?> getPropertyType() {
		return propertyType;
	}

	/**
	 * 获取比较类型.
	 */
	public MatchType getMatchType() {
		return matchType;
	}

}
