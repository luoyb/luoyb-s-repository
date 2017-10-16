package cc.cnfc.dao.joker;

import org.springframework.stereotype.Repository;

import cc.cnfc.core.orm.hibernate.SimpleHibernateDao;
import cc.cnfc.entity.joker.Album;

@Repository
public class AlbumDao extends SimpleHibernateDao<Album, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3916336739057282380L;

}
