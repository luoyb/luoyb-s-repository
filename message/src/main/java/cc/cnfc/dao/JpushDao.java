/**
 * 
 */
package cc.cnfc.dao;

import org.springframework.stereotype.Repository;

import cc.cnfc.core.orm.hibernate.SimpleHibernateDao;
import cc.cnfc.entity.JpushLog;

/**
 * @author luoyb
 *
 */
@Repository
public class JpushDao extends SimpleHibernateDao<JpushLog, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2834744485819520261L;

	// public void saveJpushLog(JpushLog jpushLog) {
	// getSession().saveOrUpdate(jpushLog);
	// }

}
