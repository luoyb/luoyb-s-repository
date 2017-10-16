package cc.cnfc.core.orm.hibernate;

/*
 * Copyright 2007 by RENWOYOU Corporation.
 * 
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of RENWOYOU Corporation ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with RENWOYOU.
 */
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cc.cnfc.core.orm.Page;

/**
 * 产生Hibernate　HQL语句，同时管理分页信息。<br>
 * <p/>
 * 在使用Hibernate进行分页查询时，可使用此类为参数，并返回相应查询结果及总计记录数(如果需要) setQueryClass(Class
 * clazz)必须设置
 * 
 * @author hongdj
 * @version 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class HibernatePage extends Page {

	private static final long serialVersionUID = -4756966998401888477L;
	/**
	 * 等于
	 */
	public static final String EQUAL = "=";
	/**
	 * 不等于
	 */
	public static final String NOT_EQUAL = "<>";
	/**
	 * 大于
	 */
	public static final String GREATER_THAN = ">";
	/**
	 * 大于等于
	 */
	public static final String GREATER_EQUAL = ">=";
	/**
	 * 小于
	 */
	public static final String LESS_THAN = "<";
	/**
	 * 小于等于
	 */
	public static final String LESS_EQUAL = "<=";
	/**
	 * 中间包含
	 */
	public static final String INCLUDE = "%value%";
	/**
	 * 左边包括
	 */
	public static final String LEFT_INCLUDE = "value%";
	/**
	 * 右边包括
	 */
	public static final String RIGHT_INCLUDE = "%value";
	/**
	 * 空值
	 */
	public static final String ISNULL = "isnull";

	/**
	 * 不为空值
	 */
	public static final String ISNOTNULL = "isnotnull";

	/**
	 * 空字符串值
	 */
	public static final String ISEMPTY = "isempty";

	/**
	 * 不为空字符串值
	 */
	public static final String ISNOTEMPTY = "isnotempty";

	/** 排序 */
	public static final String ORDER_BY = "orderBy";

	/**
	 * IN
	 */
	public static final String IN = "in";

	// 查询的hql语句
	private String hql;

	// 排序语句
	private String orderBy;

	// 分组语句
	private String groupBy;

	// 查询的HQL其它语句，可为having等
	private String otherHql;

	// 查询条件的属性名列表
	private List propertyNames;

	// 查询条件的操作符列表，与属性名列表一一对应。操作符包括=, >=, <=, <>, !=, like
	private List operators;

	// 查询条件的值列表，该列表应当与属性列表一一对应
	private List values;

	private Class clazz;

	/**
	 * 根据HttpServletRequest创建hql对象
	 * 
	 * @param request
	 */
	public HibernatePage(HttpServletRequest request) {
		super(request);
		this.hql = null;
		this.orderBy = request.getParameter(ORDER_BY);
		this.groupBy = null;
		this.propertyNames = new ArrayList();
		this.operators = new ArrayList();
		this.values = new ArrayList();
		this.otherHql = null;
	}

	@SuppressWarnings("rawtypes")
	public HibernatePage() {
		super();
		this.hql = null;
		this.orderBy = null;
		this.groupBy = null;
		this.propertyNames = new ArrayList();
		this.operators = new ArrayList();
		this.values = new ArrayList();
		this.otherHql = null;
	}

	/**
	 * 设置查询条件(where后面),该方法迭加多次调用。 示例如下<br>
	 * 
	 * <pre>
	 * hqlWrapper.setCondition(&quot;codeType&quot;, HqlWrapper.EQUAL, codeType);
	 * hqlWrapper.setCondition(&quot;codeTypeName&quot;, HqlWrapper.EQUAL, codeTypeName);
	 * </pre>
	 * 
	 * @param propertyName
	 *            查询条件的属性名
	 * @param operator
	 *            查询条件的操作符
	 * @param value
	 *            查询条件的值
	 */
	public void setCondition(String propertyName, String operator, Object value) {

		if (propertyName == null || propertyName.trim().equals(""))
			return;
		if (operator == null || operator.trim().equals("")) {
			operator = EQUAL;
		}

		if (operator.equals(ISNULL) || operator.equals(ISNOTNULL)
				|| operator.equals(ISEMPTY) || operator.equals(ISNOTEMPTY)) {
			value = operator;
		}

		if (value == null)
			return;
		if (value instanceof String && value.toString().trim().equals(""))
			return;
		propertyNames.add(propertyName);

		if (operator.equals(INCLUDE)) {
			operators.add("like");
			values.add("%" + value + "%");
		} else if (operator.equals(LEFT_INCLUDE)) {
			operators.add("like");
			values.add(value + "%");
		} else if (operator.equals(RIGHT_INCLUDE)) {
			operators.add("like");
			values.add("%" + value);
		} else {
			operators.add(operator);
			values.add(value);
		}
	}

	public void setConditionEqual(String propertyName, Object value) {
		setCondition(propertyName, EQUAL, value);
	}

	public void setConditionNotEqual(String propertyName, Object value) {
		setCondition(propertyName, NOT_EQUAL, value);
	}

	public void setConditionGreaterEqual(String propertyName, Object value) {
		setCondition(propertyName, GREATER_EQUAL, value);
	}

	public void setConditionGreaterThan(String propertyName, Object value) {
		setCondition(propertyName, GREATER_THAN, value);
	}

	public void setConditionLessEqual(String propertyName, Object value) {
		setCondition(propertyName, LESS_EQUAL, value);
	}

	public void setConditionLessThan(String propertyName, Object value) {
		setCondition(propertyName, LESS_THAN, value);
	}

	public void setConditionLeftInclude(String propertyName, Object value) {
		setCondition(propertyName, LEFT_INCLUDE, value);
	}

	public void setConditionRightInclude(String propertyName, Object value) {
		setCondition(propertyName, RIGHT_INCLUDE, value);
	}

	public void setConditionInclude(String propertyName, Object value) {
		setCondition(propertyName, INCLUDE, value);
	}

	public void setConditionIsNull(String propertyName) {
		setCondition(propertyName, ISNULL, ISNULL);
	}

	public void setConditionIsNotNull(String propertyName) {
		setCondition(propertyName, ISNOTNULL, ISNOTNULL);
	}

	public void setConditionIsEmpty(String propertyName) {
		setCondition(propertyName, ISEMPTY, ISEMPTY);
	}

	public void setConditionIsNotEmpty(String propertyName) {
		setCondition(propertyName, ISNOTEMPTY, ISNOTEMPTY);
	}

	/**
	 * 获取查询的hql语句。<br>
	 * <p/>
	 * 说明：<br>
	 * 1)如果有设置condition，则hql为select ... from ...部分(不带where条件)，否则到2）；<br>
	 * 2)如果没有设置condition但设置order by ,group by ,otherHql(having等)，<br>
	 * 则hql为select ... from ... where ...部分(不带order by/group by/having)，否则到3)；<br>
	 * 3)如果condition及order by ,group by ,otherHql(having等)均没有设置，<br>
	 * 则hql为select ... from ... where ... order by ...（完整hql语句）。
	 * 
	 * @return String 获取查询的hql语句
	 */
	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置排序条件。<br>
	 * 示例：<br>
	 * <p/>
	 * hqlWrapper.setOrderBy("a desc,b asc"); -->order by a desc,b asc
	 * 
	 * @param orderBy
	 *            排序字符串
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getGroupBy() {
		return groupBy;
	}

	/**
	 * 设置分组条件。<br>
	 * 示例：<br>
	 * <p/>
	 * hqlWrapper.setGroupBy("a,b"); --> group by a,b
	 * 
	 * @param groupBy
	 *            分组字符串
	 */
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	/**
	 * 查询的HQL其它语句，如having等
	 * 
	 * @return String
	 */
	public String getOtherHql() {
		return otherHql;
	}

	public void setOtherHql(String otherHql) {
		this.otherHql = otherHql;
	}

	/**
	 * 查询条件的属性名列表
	 * 
	 * @return Set
	 */
	@SuppressWarnings("rawtypes")
	public List getPropertyNames() {
		return propertyNames;
	}

	public void setPropertyNames(List propertyNames) {
		this.propertyNames = propertyNames;
	}

	/**
	 * 查询条件的操作符列表，与属性名列表一一对应。操作符包括=, >=, <=, <>, !=, like
	 * 
	 * @return Set
	 */
	public List getOperators() {
		return operators;
	}

	public void setOperators(List operators) {
		this.operators = operators;
	}

	/**
	 * 查询条件的值列表，该列表应当与属性列表一一对应
	 * 
	 * @return Set
	 */
	public List getValues() {
		return values;
	}

	public void setValues(List values) {
		this.values = values;
	}

	public void setQueryClass(Class clazz) {
		this.clazz = clazz;

	}

	public Class getQueryClass() {
		return clazz;

	}

}
