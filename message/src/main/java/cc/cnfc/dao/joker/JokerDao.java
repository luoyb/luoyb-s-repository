package cc.cnfc.dao.joker;

import org.springframework.stereotype.Repository;

import cc.cnfc.core.orm.hibernate.SimpleHibernateDao;
import cc.cnfc.entity.joker.Joker;

@Repository
public class JokerDao extends SimpleHibernateDao<Joker, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8392703787988134018L;

}
