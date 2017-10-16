/**
 * @(#)HibernateDao.java 2009-12-8
 * 
 *                       Copyright 2000-2009 by RENWOYOU Corporation.
 *
 *                       All rights reserved.
 *
 *                       This software is the confidential and proprietary information of RENWOYOU Corporation ("Confidential Information"). You shall not disclose such Confidential Information and
 *                       shall use it only in accordance with the terms of the license agreement you entered into with RENWOYOU.
 * 
 */
package cc.cnfc.core.orm.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import cc.cnfc.core.exception.GenericException;
import cc.cnfc.core.utils.StringUtil;

/**
 * 封装查询功能的Hibernat DAO基类.主要包括HibernatePage分页查询
 * 
 * 直接在service中调用。由于在BaseService中直接按类型注入，DAO不可被继承。 具体的DAO使用SimpleHibernateDao继承
 * 
 * @author fwgroup
 */
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class HibernateDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private SessionFactory sessionFactory;

	/**
	 * 取得sessionFactory.
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 采用@Autowired按类型注入SessionFactory,当有多个SesionFactory的时候Override本函数.
	 */
	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 取得当前Session.
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 根据HQueryInfo(Hibernate查询条件及分页管理包装类)查询符合条件的对象，
	 * 把查询结果list及count放置到该HQueryInfo并返回
	 * 
	 * @param queryInfo
	 *            Hibernate查询条件及分页管理包装类
	 * @return HQueryInfo 包含查询结果list及count
	 */
	public HibernatePage query(final HibernatePage queryInfo) {
		if (queryInfo == null)
			return null;

		// 格式化参数值

		String className = queryInfo.getQueryClass().getName();
		String hql = " from " + className;
		List propertyNamesLs = queryInfo.getPropertyNames();
		String[] propertyNames = (String[]) propertyNamesLs
				.toArray(new String[propertyNamesLs.size()]);
		List operatorsLs = queryInfo.getOperators();
		String[] operators = (String[]) operatorsLs
				.toArray(new String[operatorsLs.size()]);
		List valuesList = queryInfo.getValues();
		Object[] values = valuesList == null || valuesList.size() == 0 ? null
				: valuesList.toArray();

		int offset = queryInfo.getOffset();
		int size = queryInfo.getPageSize();
		boolean isTotalSize = queryInfo.isCountTotalSize();
		String orderBy = queryInfo.getOrderBy();
		String groupBy = queryInfo.getGroupBy();
		String otherCause = queryInfo.getOtherHql();
		// 执行查询
		Object[] result = this.query(hql, propertyNames, operators, values,
				offset, size, isTotalSize, orderBy, groupBy, otherCause);

		// 返回查询结果并把结果设置给QueryInfo
		queryInfo.setQueryList((List) result[0]);
		queryInfo.setRecordCount(result[1] != null ? ((Integer) result[1])
				.intValue() : 0);

		// 返回QueryInfo
		return queryInfo;
	}

	public Object[] query(final String hql, final String[] propertyNames,
			final String[] operators, final Object[] values, final int offset,
			final int size, final boolean isTotalSize, final String orderBy,
			final String groupBy, final String otherCause) {
		return this.query(hql, propertyNames, operators, values, offset, size,
				isTotalSize, orderBy, groupBy, otherCause, false);
	}

	/**
	 * 根据条件使用HQL语句查询数据。
	 *
	 * 具有功能： 1）支持查询分页，该方法会利用数据库本身的分页技术实现。说明如下： a)如果数据库(如MySQL,Oracle,SQL
	 * Server2005等)支持limit n,m，查询效率最高； b)如果数据库(如informix,Sybase 12.5.3,SQL
	 * Server等)支持top n，查询效率次之（查询结果越大，效率越低）； c)如果以上两种均不支持，查询效率最低。 2）支持查询总记录数
	 * 3）支持order by，group by,having等 编写查询方法 Query q= session.createQuery(
	 * "select new A(M.a,N.b) from M as M,N as N where M.id=N.id");
	 *
	 * @param hql
	 *            HQL查询语句（不带Where条件）。不允许在select段内使用子查询，如不允许这种用法： select
	 *            a,b,(select c from table1) as d from table2 ...
	 *            1)对于查询全部对象属性，(select *)不可写。如：from TUser；
	 *            2)对于查询部分对象属性，则必须写完整，如：select userName,password from TUser;
	 * @param propertyNames
	 *            查询条件的属性名列表
	 * @param operators
	 *            查询条件的操作符列表，如果查询条件中存在不为=的操作符，需要填写该列表，否则为null，
	 *            应与属性名列表一一对应。操作符包括=, >=, <=, <>, !=, like。
	 * @param values
	 *            查询条件的值列表，该列表应当与属性列表一一对应
	 * @param offset
	 *            查询结果的起始行，从0开始。如果不需要，则设置为-1。
	 * @param size
	 *            查询结果的最大行数。如果不需要，则设置为-1
	 * @param isTotalSize
	 *            是否需要返回本次查询的总记录数，默认true
	 * @param orderBy
	 *            排序字符串,不含order by字符串，如orderBy="a desc,b asc",最后生成为：order by a
	 *            desc,b asc
	 * @param groupBy
	 *            分组字符串,不含group by 字符串，如groupBy="a desc,b asc",最后生成为：group by a
	 *            desc,b asc
	 * @param otherCause
	 *            where后面的其它语句，如排序(order by),分组(group by)及聚集(having)。
	 * @param isCache
	 *            是否将结果集放置到cache 如"group by name order by name desc"
	 * @return Object[] 有两个值，第一个值表示查询结果列表List list = (List)Object[0]
	 *         第二个表示查询返回的总记录数，int count = ((Integer)Object[1]).intValue;
	 * @throws com.rwy.base.core.exception.GenericException
	 * 
	 */
	public Object[] query(final String hql, final String[] propertyNames,
			final String[] operators, final Object[] values, final int offset,
			final int size, final boolean isTotalSize, final String orderBy,
			final String groupBy, final String otherCause, final boolean isCache)
			throws DataAccessException {
		Assert.hasText(hql, "hql不能为空");

		Query query;
		String countSql;
		String fullSql;
		Integer count = 0;
		Map map = new HashMap();
		String where = "";

		if (propertyNames != null && propertyNames.length > 0 && values != null
				&& values.length > 0) {
			if (propertyNames.length != values.length) {
				throw new GenericException();
			}

			if (operators != null && propertyNames.length != operators.length) {
				logger.error("");
				throw new GenericException();
			}

			for (int i = 0; i <= propertyNames.length - 1; i++) {
				if ("".equals(where)) {
					where = " where ";
				} else {
					where += "and ";
				}
				if (operators != null
						&& operators[i].equalsIgnoreCase("isnull")) {
					where += propertyNames[i] + " is null ";
				} else if (operators != null
						&& operators[i].equalsIgnoreCase("isnotnull")) {
					where += propertyNames[i] + " is not null ";
				} else if (operators != null
						&& operators[i].equalsIgnoreCase("isempty")) {
					where += propertyNames[i] + " = '' ";
				} else if (operators != null
						&& operators[i].equalsIgnoreCase("isnotempty")) {
					where += propertyNames[i] + " <> '' ";
				} else if (operators != null
						&& operators[i].equalsIgnoreCase("in")) {
					where += propertyNames[i] + " in ";
				} else {
					where += propertyNames[i]
							+ (operators == null || operators[i] == null ? "="
									: " " + operators[i]) + " ? ";
				}
			}

			fullSql = hql + where;
			fullSql += otherCause == null || otherCause.trim().equals("") ? ""
					: " " + otherCause;

			fullSql += groupBy == null || groupBy.trim().equals("") ? ""
					: " group by " + groupBy;
			fullSql += orderBy == null || orderBy.trim().equals("") ? ""
					: " order by " + orderBy;

			query = isCache ? this.createQuery(fullSql).setCacheable(true)
					: this.createQuery(fullSql);

			int paramIndex = 0;
			for (int i = 0; i <= values.length - 1; i++) {
				if (operators != null
						&& operators[i].equalsIgnoreCase("isnull"))
					continue;
				if (operators != null
						&& operators[i].equalsIgnoreCase("isnotnull"))
					continue;
				if (operators != null
						&& operators[i].equalsIgnoreCase("isempty"))
					continue;
				if (operators != null
						&& operators[i].equalsIgnoreCase("isnotempty"))
					continue;
				// 添加IN操作符
				if (operators != null && operators[i].equalsIgnoreCase("in")) {
					values[i] = StringUtil.getInString(values[i]);
				}
				query.setParameter(paramIndex++, values[i]);
			}

		} else {
			if ("".equals(where) && hql.indexOf("where") == -1) {
				where = " where 1=1 ";
			} else if (!"".equals(where)) {
				where += " and ";
			}

			fullSql = hql + where;
			fullSql += otherCause == null || otherCause.trim().equals("") ? ""
					: " " + otherCause;

			fullSql += groupBy == null || groupBy.trim().equals("") ? ""
					: " group by " + groupBy;
			fullSql += orderBy == null || orderBy.trim().equals("") ? ""
					: " order by " + orderBy;

			query = this.createQuery(fullSql);
		}

		// 如果需要统计本次查询总记录数
		if (isTotalSize) {

			// 生成统计总数查询语句（不能累加order by ，否则效率会受影响）
			countSql = hql + where;

			// modi by hongdj

			countSql += groupBy == null || groupBy.trim().equals("") ? ""
					: " group by " + groupBy;
			countSql += otherCause == null || otherCause.trim().equals("") ? ""
					: " " + otherCause;

			// 生成查询总记录的hql语句。在hql中，不允许在select段内使用子查询，如不允许这种用法：
			// select a,b,(select c from table1) as d from table2 ...
			countSql = "select count(*) from "
					+ countSql
							.substring(countSql.toLowerCase().indexOf("from") + 5);
			Query query2 = this.createQuery(countSql);

			int paramIndex = 0;
			if (values != null) {
				for (int i = 0; i <= values.length - 1; i++) {
					if (operators != null
							&& operators[i].equalsIgnoreCase("isnull"))
						continue;
					if (operators != null
							&& operators[i].equalsIgnoreCase("isnotnull"))
						continue;
					if (operators != null
							&& operators[i].equalsIgnoreCase("isempty"))
						continue;
					if (operators != null
							&& operators[i].equalsIgnoreCase("isnotempty"))
						continue;
					query2.setParameter(paramIndex++, values[i]);
				}
			}
			// modi by denny
			Long res = (Long) query2.uniqueResult();
			if (res != null) {
				count = res.intValue();
			}
		}

		if (offset > 0) {
			query.setFirstResult(offset);
		}

		if (size > 0) {
			query.setMaxResults(size);
		}

		// 输出查询语句到log
		// logger.debug("fullSql=" + query.getQueryString());

		map.put("list", query.list());
		map.put("count", count);
		return new Object[] { map.get("list"), map.get("count") };
	}

	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public Query createQuery(final String queryString,
			final Map<String, Object> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

}
