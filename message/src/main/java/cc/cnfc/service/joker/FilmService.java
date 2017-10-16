package cc.cnfc.service.joker;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.cnfc.core.orm.Page;
import cc.cnfc.core.orm.hibernate.HibernatePage;
import cc.cnfc.core.service.BaseService;
import cc.cnfc.entity.joker.Film;
import cc.cnfc.entity.joker.FilmComment;

@Service
@Transactional
public class FilmService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -798324665781470526L;

	public Page findFilms(String orderBy) {
		HibernatePage hibernatePage = new HibernatePage();
		hibernatePage.setQueryClass(Film.class);
		// hibernatePage.setConditionEqual("mSysId", mSysId);
		hibernatePage.setOrderBy(orderBy);
		Page page = this.query(hibernatePage);
		return page;
	}

	public Page findFilmComment(Long filmId) {
		HibernatePage hibernatePage = new HibernatePage();
		hibernatePage.setQueryClass(FilmComment.class);
		hibernatePage.setConditionEqual("filmId", filmId);
		hibernatePage.setOrderBy("createTime desc");
		Page page = this.query(hibernatePage);
		return page;
	}

}
