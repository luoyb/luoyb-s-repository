/**
 * 
 */
package cc.cnfc.dao;

import org.springframework.stereotype.Repository;

import cc.cnfc.core.orm.hibernate.SimpleHibernateDao;
import cc.cnfc.entity.SmsBlacklist;

/**
 * @author luoyb
 *
 */
@Repository
public class SmsBlacklistDao extends SimpleHibernateDao<SmsBlacklist, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1734907823981016343L;

}
