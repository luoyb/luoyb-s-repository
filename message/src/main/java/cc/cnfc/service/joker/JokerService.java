package cc.cnfc.service.joker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.cnfc.core.orm.Page;
import cc.cnfc.core.orm.hibernate.HibernatePage;
import cc.cnfc.core.service.BaseService;
import cc.cnfc.dao.joker.JokerDao;
import cc.cnfc.entity.joker.Joker;

@Service
@Transactional
public class JokerService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -798324665781470526L;

	@Autowired
	private JokerDao jokerDao;

	public Page findJokers(String orderBy) {
		HibernatePage hibernatePage = new HibernatePage();
		hibernatePage.setQueryClass(Joker.class);
		// hibernatePage.setConditionEqual("mSysId", mSysId);
		hibernatePage.setOrderBy(orderBy);
		Page page = this.query(hibernatePage);
		return page;
	}

	/**
	 * 添加一个赞
	 * 
	 * @param jokerId
	 */
	public void addLikeCount(Long jokerId) {
		Joker joker = jokerDao.get(jokerId);
		joker.setLikeCount(joker.getLikeCount() + 1);
		jokerDao.save(joker);
	}

}
