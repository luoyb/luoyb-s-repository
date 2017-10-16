package cc.cnfc.dao;

import org.springframework.stereotype.Repository;

import cc.cnfc.core.orm.hibernate.SimpleHibernateDao;
import cc.cnfc.entity.ac.Customer;

@Repository
public class CustomerDao extends SimpleHibernateDao<Customer, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3084783634746154015L;

	public Customer findByMSysId(String mSysId) {
		return this.findUniqueBy("mSysId", mSysId);
	}

	public Customer findByWxSerialNum(String wxSerialNum) {
		return this.findUniqueBy("wxSerialNum", wxSerialNum);
	}

}
