/**
 * 
 */
package cc.cnfc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cc.cnfc.core.orm.hibernate.SimpleHibernateDao;
import cc.cnfc.entity.SmsLog;

/**
 * @author luoyb
 *
 */
@Repository
public class SmsDao extends SimpleHibernateDao<SmsLog, Long> {

	// public void saveSms(SmsLog smsLog) {
	// getSession().saveOrUpdate(smsLog);
	// }

	/**
	 * 
	 */
	private static final long serialVersionUID = -5828734565484059416L;

	public List<SmsLog> findByMSysId(String mSysId) {
		return this.findBy("mSysId", mSysId);
	}

}
