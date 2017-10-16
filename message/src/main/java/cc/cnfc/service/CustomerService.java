package cc.cnfc.service;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.cnfc.core.orm.Page;
import cc.cnfc.core.orm.hibernate.HibernatePage;
import cc.cnfc.core.service.BaseService;
import cc.cnfc.dao.CustomerDao;
import cc.cnfc.dao.TestCustomerRoleDto;
import cc.cnfc.entity.ac.Customer;
import cc.cnfc.mapper.CustomerMapper;

@Service
@Transactional
public class CustomerService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8195737280133303479L;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private CustomerMapper customerMapper;

	public Page list() {
		HibernatePage hibernatePage = new HibernatePage();
		hibernatePage.setQueryClass(Customer.class);
		hibernatePage.setOrderBy("mSysId");
		Page page = this.query4JqGrid(hibernatePage);
		return page;
	}

	public Customer findByMSysId(String mSysId) {
		return customerDao.findByMSysId(mSysId);
	}

	public Customer findByWxSerialNum(String wxSerialNum) {
		return customerDao.findByWxSerialNum(wxSerialNum);
	}

	public List<Customer> findAll() {
		return customerDao.getAll();
	}

	public Customer findLoginCustomer() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			return this.findByMSysId(subject.getPrincipal().toString());
		}
		throw new RuntimeException("您未登录，不能进行操作");
	}

	// /////test

	public TestCustomerRoleDto find(Customer customer) {
		return customerMapper.find(customer);
	}

	public Customer findByM(String mSysId) {
		return customerMapper.findByMSysId(mSysId);
	}

	public List<Customer> findLs() {
		// PageHelper.startPage(1, 2);
		return customerMapper.findLs(3, 10);
	}
}
