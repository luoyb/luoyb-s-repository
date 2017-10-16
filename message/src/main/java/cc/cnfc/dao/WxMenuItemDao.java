/**
 * 
 */
package cc.cnfc.dao;

import org.springframework.stereotype.Repository;

import cc.cnfc.core.orm.hibernate.SimpleHibernateDao;
import cc.cnfc.entity.WxMenuItem;

/**
 * @author luoyb
 *
 */
@Repository
public class WxMenuItemDao extends SimpleHibernateDao<WxMenuItem, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8022075738060472539L;

}
