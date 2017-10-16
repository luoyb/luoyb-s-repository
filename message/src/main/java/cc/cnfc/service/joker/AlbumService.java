package cc.cnfc.service.joker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.cnfc.core.orm.Page;
import cc.cnfc.core.orm.hibernate.HibernatePage;
import cc.cnfc.core.service.BaseService;
import cc.cnfc.dao.joker.AlbumDao;
import cc.cnfc.entity.joker.Album;
import cc.cnfc.entity.joker.AlbumDetail;

@Service
@Transactional
public class AlbumService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -798324665781470526L;

	@Autowired
	private AlbumDao albumDao;

	public Page findAlbums(String orderBy) {
		HibernatePage hibernatePage = new HibernatePage();
		hibernatePage.setQueryClass(Album.class);
		// hibernatePage.setConditionEqual("mSysId", mSysId);
		hibernatePage.setOrderBy(orderBy);
		Page page = this.query(hibernatePage);
		return page;
	}

	public Page albumDetail(Long albumId) {
		HibernatePage hibernatePage = new HibernatePage();
		hibernatePage.setQueryClass(AlbumDetail.class);
		hibernatePage.setConditionEqual("albumId", albumId);
		hibernatePage.setOrderBy("createTime desc");
		Page page = this.query(hibernatePage);
		return page;
	}

}
