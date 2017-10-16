/**
 * 
 */
package cc.cnfc.service.sms;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.cnfc.core.orm.Page;
import cc.cnfc.core.orm.hibernate.HibernatePage;
import cc.cnfc.core.service.BaseService;
import cc.cnfc.dao.SmsBlacklistDao;
import cc.cnfc.entity.SmsBlacklist;

/**
 * @author luoyb
 *
 */
@Service
@Transactional
public class SmsBlacklistService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6152465944967881783L;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SmsBlacklistDao smsBlacklistDao;
	@Autowired
	private SmsService smsSvc;

	public void add(String mSysId, String mobiles, String reason) {
		List<String> mobileLs = smsSvc.assembleMobile(mobiles);
		for (String _mobiles : mobileLs) {
			SmsBlacklist smsBlacklist = new SmsBlacklist();
			smsBlacklist.setmSysId(mSysId);
			smsBlacklist.setMobiles(_mobiles);
			smsBlacklist.setReason(reason);
			smsBlacklist.setCreateTime(new Date());
			smsBlacklist.setUpdateTime(new Date());
			this.add(smsBlacklist);
		}
	}

	public void update(String mSysId, Long id, String mobiles, String reason) {
		this.checkIsSelfData(mSysId, id);
		SmsBlacklist smsBlacklist = this.load(SmsBlacklist.class, id);
		mobiles = mobiles.replaceAll("，", ",");
		smsBlacklist.setMobiles(mobiles);
		smsBlacklist.setReason(reason);
		smsBlacklist.setUpdateTime(new Date());
		this.update(smsBlacklist);
	}

	public void delete(String mSysId, Long id) {
		this.checkIsSelfData(mSysId, id);
		this.delete(SmsBlacklist.class, id);
	}

	/**
	 * 检查某条数据是否是自己的
	 * 
	 * @param mSysId
	 * @param id
	 */
	private void checkIsSelfData(String mSysId, Long id) {
		List<SmsBlacklist> blacklistLs = smsBlacklistDao.find("from SmsBlacklist where mSysId=? and id=?", mSysId, id);
		if (CollectionUtils.isEmpty(blacklistLs)) {
			throw new RuntimeException("您无权限操作该数据！");
		}
	}

	public Page list(String mSysId) {
		HibernatePage hibernatePage = new HibernatePage();
		hibernatePage.setQueryClass(SmsBlacklist.class);
		hibernatePage.setConditionEqual("mSysId", mSysId);
		hibernatePage.setOrderBy("createTime desc");
		Page page = this.query4JqGrid(hibernatePage);
		return page;
	}

	/**
	 * 过滤黑名单用户
	 * 
	 * @param mobiles
	 */
	public String filterBlacklist(String mSysId, String mobiles) {
		String[] mobileArr = mobiles.split(",|，");
		Set<String> mobileSet = new HashSet<String>();
		for (String mobile : mobileArr) {
			if (this.isBlacklist(mSysId, mobile)) {
				logger.info("mSysId=" + mSysId + " , " + mobile + " is blacklist , not send.");
			} else {
				mobileSet.add(mobile);
			}
		}
		String filterMobiles = StringUtils.join(mobileSet.toArray(), ",");
		return filterMobiles;
	}

	@SuppressWarnings("unchecked")
	public boolean isBlacklist(String mSysId, String mobile) {
		List<SmsBlacklist> blacklistLs = smsBlacklistDao.createSQLQuery(
				"select * from m_sms_blacklist where mSysId=? and find_in_set(?,mobiles)", mSysId, mobile).list();
		if (CollectionUtils.isNotEmpty(blacklistLs)) {
			return true;
		}
		return false;
	}

	public List<SmsBlacklist> getAll(String mSysId) {
		List<SmsBlacklist> blacklistLs = smsBlacklistDao.findBy("mSysId", mSysId);
		return blacklistLs;
	}

	public static void main(String[] args) {
		Set<String> mobileSet = new HashSet<String>();
		String bb = StringUtils.join(mobileSet.toArray(), ",");
		System.out.print(bb);
	}
}
