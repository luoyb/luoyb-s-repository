package cc.cnfc.service.joker;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.cnfc.core.orm.Page;
import cc.cnfc.core.orm.hibernate.HibernatePage;
import cc.cnfc.core.service.BaseService;
import cc.cnfc.entity.joker.Music;
import cc.cnfc.entity.joker.MusicComment;

@Service
@Transactional
public class MusicService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -798324665781470526L;

	public Page findMusics(String orderBy) {
		HibernatePage hibernatePage = new HibernatePage();
		hibernatePage.setQueryClass(Music.class);
		// hibernatePage.setConditionEqual("mSysId", mSysId);
		hibernatePage.setOrderBy(orderBy);
		Page page = this.query(hibernatePage);
		return page;
	}

	public Page findMusicComment(Long musicId) {
		HibernatePage hibernatePage = new HibernatePage();
		hibernatePage.setQueryClass(MusicComment.class);
		hibernatePage.setConditionEqual("musicId", musicId);
		hibernatePage.setOrderBy("createTime desc");
		Page page = this.query(hibernatePage);
		return page;
	}

}
