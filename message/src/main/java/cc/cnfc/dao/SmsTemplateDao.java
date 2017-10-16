/**
 * 
 */
package cc.cnfc.dao;

import org.springframework.stereotype.Repository;

import cc.cnfc.core.orm.hibernate.SimpleHibernateDao;
import cc.cnfc.entity.SmsTemplate;

/**
 * @author luoyb
 *
 */
@Repository
public class SmsTemplateDao extends SimpleHibernateDao<SmsTemplate, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7938975438079964992L;

}
