/*
 * Copyright 2007 by RENWOYOU Corporation.
 * 
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of RENWOYOU Corporation ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with RENWOYOU.
 */

package cc.cnfc.core.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cc.cnfc.core.orm.Page;
import cc.cnfc.core.orm.PropertyFilter;
import cc.cnfc.core.orm.hibernate.HibernateDao;
import cc.cnfc.core.orm.hibernate.HibernatePage;
import cc.cnfc.pub.constant.Const;

/**
 * service基类，含 ibatis和hibernate分页查询。 可单独使用，也可继承使用。
 * 
 * @author denny, Zhiwei HUANG
 * @date 2009-12-22
 * @version 1.0
 */
@Transactional
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class BaseService implements Serializable {

	private static final long serialVersionUID = 4152141546179443432L;

	/** 程序运行的调试日志Logger */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/** 记录业务操作数据库日志的Logger */
	protected Logger dbLogger = LoggerFactory.getLogger("dbOpLog");

	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected SessionFactory sessionFactory;
	@Autowired
	protected HibernateDao hibernateDao;

	/**
	 * 取得对象的主键名.
	 */
	public String getIdName(Class<?> clazz) {
		ClassMetadata meta = sessionFactory.getClassMetadata(clazz);
		return meta.getIdentifierPropertyName();
	}

	/**
	 * 取得当前Session.
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * flush，避免hibernate与jdbc混用，出现查询不到数据的bug
	 */
	public void flush() {
		getSession().flush();
	}

	/**
	 * 封装hibernate的查询条件,前台须满足一定的命名规范,详见PropertyFilter.java
	 * 
	 * @param request
	 * @param clazz
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Page queryByHibernate(HttpServletRequest request, Class clazz) {
		return query(request, clazz, null);
	}

	/**
	 * @param request
	 * @param ibatisSql
	 * @param clazz
	 * @param param
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	private Page query(HttpServletRequest request, Class clazz, Map param) {
		java.util.List<PropertyFilter> list = new ArrayList<PropertyFilter>();
		Map map = request.getParameterMap();
		java.util.Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String val = request.getParameter(key);
			if (key.indexOf("q_") == -1 || StringUtils.isEmpty(val)) {
				continue;
			}
			PropertyFilter filter = new PropertyFilter(key, val);
			list.add(filter);
		}
		Page page = null;
		HibernatePage hibernatePage = new HibernatePage(request);
		hibernatePage.setQueryClass(clazz);
		for (PropertyFilter filter : list) {
			hibernatePage.setCondition(filter.getPropertyName(), filter.getMatchType().getValue(),
					filter.getPropertyValue());
		}
		query(hibernatePage);
		page = hibernatePage;
		return page;

	}

	/**
	 * 根据HibernatePage(Hibernate查询条件及分页管理包装类)查询符合条件的对象， 把查询结果list及count放置到该Page并返回
	 * 
	 * @param hibernatePage
	 *            Hibernate查询条件及分页管理包装类
	 * @return HibernatePage 包含查询结果list及count
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public HibernatePage query(final HibernatePage hibernatePage) {
		String pageNo = (StringUtils.isBlank(request.getParameter("pageNo")) ? "1" : request.getParameter("pageNo"));
		hibernatePage.setPageNo(Integer.valueOf(pageNo));
		hibernatePage.setPageSize(Const.PAGE_SIZE);
		return hibernateDao.query(hibernatePage);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public HibernatePage query4JqGrid(final HibernatePage hibernatePage) {
		String pageNo = (StringUtils.isBlank(request.getParameter("page")) ? "1" : request.getParameter("page"));
		int pageSize = (StringUtils.isBlank(request.getParameter("rows")) ? Const.PAGE_SIZE : Integer.valueOf(request
				.getParameter("rows")));
		hibernatePage.setPageNo(Integer.valueOf(pageNo));
		hibernatePage.setPageSize(pageSize);
		return hibernateDao.query(hibernatePage);
	}

	// -- CRUD --//

	/**
	 * 增加前校验
	 */
	public void beforeAdd(Object vo) {
	};

	/**
	 * 修改前校验
	 */
	public void beforeUpdate(Object vo) {
	};

	/**
	 * 删除前校验
	 */
	public void beforeDelete(Object vo) {
	};

	/**
	 * @param vo
	 */
	public void add(Object vo) {
		beforeAdd(vo);
		getSession().save(vo);
	}

	/**
	 * @param vo
	 */
	public void update(Object vo) {
		beforeUpdate(vo);
		getSession().update(vo);
	}

	// 自动强转成所需类型
	/**
	 * @param entityClass
	 * @param id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public <X> X load(Class<?> entityClass, Serializable id) {
		X obj = (X) getSession().get(entityClass, id);
		return obj;
	}

	/**
	 * @param entityClass
	 * @param id
	 */
	public void delete(Class entityClass, Serializable id) {
		this.delete(this.load(entityClass, id));
	}

	/**
	 * @param entity
	 */
	public void delete(Object entity) {
		beforeDelete(entity);
		getSession().delete(entity);
	}

	/**
	 * 获取属性值的重复数
	 * 
	 * @param object
	 *            所要查询的实例，必须是个hibernate entity instance
	 * @param propertyName
	 *            所查询的属性名称
	 * @param propertyValue
	 *            所查询的属性值
	 * @param idValue
	 *            排除查询的标识ID
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	protected int checkPropertyExist(Object object, String propertyName, Serializable propertyValue,
			Serializable idValue) {
		StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM ");
		sb.append(object.getClass().getSimpleName()).append(" WHERE ");
		sb.append(propertyName).append(" = :propertyValue");
		if (idValue != null) {
			sb.append(" AND ").append(this.getIdName(object.getClass()));
			sb.append(" <> :idValue");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("propertyValue", propertyValue);
		paramMap.put("idValue", idValue);
		Query q = hibernateDao.createQuery(sb.toString(), paramMap);
		// hib自3.2起，为了和JPA兼容，uniqueResult返回 Long型。故使用兼容写法
		return ((Number) q.uniqueResult()).intValue();
	}

	/**
	 * @param clazz
	 * @param map
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List findByProperty(Class clazz, Map<String, Object> map) {
		StringBuilder sb = new StringBuilder(" FROM ");
		sb.append(clazz.getSimpleName()).append(" WHERE 1=1");
		if (map != null) {
			for (String str : map.keySet()) {
				sb.append(" AND ").append(str).append(" = :" + str);
			}
		}
		return hibernateDao.createQuery(sb.toString(), map).list();
	}

	/**
	 * @param queryString
	 * @param values
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Query createQuery(final String queryString, final Object... values) {
		return hibernateDao.createQuery(queryString, values);
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Query createQuery(final String queryString, final Map<String, Object> values) {
		return hibernateDao.createQuery(queryString, values);
	}

	protected HibernateDao getHibernateDao() {
		return hibernateDao;
	}

	/**
	 * 判断名字是否已经使用过。
	 * 
	 * @param className
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Boolean isPropertyValueExisted(String className, String propertyName, String propertyValue) {
		if (StringUtils.isBlank(className) || StringUtils.isBlank(propertyName) || StringUtils.isBlank(propertyValue)) {
			return Boolean.TRUE;
		}
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (Exception e) {
			logger.error("Can't find the right class.", e);
			return Boolean.TRUE;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(propertyName, propertyValue);
		List<?> result = this.findByProperty(clazz, params);
		if (CollectionUtils.isNotEmpty(result)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}