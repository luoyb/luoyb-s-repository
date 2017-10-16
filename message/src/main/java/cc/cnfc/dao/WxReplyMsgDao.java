/**
 * 
 */
package cc.cnfc.dao;

import org.springframework.stereotype.Repository;

import cc.cnfc.core.orm.hibernate.SimpleHibernateDao;
import cc.cnfc.entity.WxReplyMsg;

/**
 * @author luoyb
 *
 */
@Repository
public class WxReplyMsgDao extends SimpleHibernateDao<WxReplyMsg, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8489311757962185478L;

}
