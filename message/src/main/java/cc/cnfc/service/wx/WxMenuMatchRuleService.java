package cc.cnfc.service.wx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.cnfc.core.service.BaseService;
import cc.cnfc.dao.WxMenuMatchRuleDao;

@Service
@Transactional
public class WxMenuMatchRuleService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4621549834439974544L;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WxMenuMatchRuleDao wxMenuMatchRuleDao;

}
