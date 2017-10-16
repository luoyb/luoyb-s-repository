/**
 * 
 */
package cc.cnfc.dao;

import org.springframework.stereotype.Repository;

import cc.cnfc.core.orm.hibernate.SimpleHibernateDao;
import cc.cnfc.entity.TempSms;

/**
 * @author luoyb
 *
 */
@Repository
public class TempSmsDao extends SimpleHibernateDao<TempSms, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2692971482653992517L;

}
