/**
 * 
 */
package cc.cnfc.dao;

import org.springframework.stereotype.Repository;

import cc.cnfc.core.orm.hibernate.SimpleHibernateDao;
import cc.cnfc.entity.WxMenuMatchRule;

/**
 * @author luoyb
 *
 */
@Repository
public class WxMenuMatchRuleDao extends SimpleHibernateDao<WxMenuMatchRule, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7784578158757982034L;

}
