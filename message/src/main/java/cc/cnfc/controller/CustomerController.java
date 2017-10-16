package cc.cnfc.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.cnfc.core.orm.Page;
import cc.cnfc.dao.TestCustomerRoleDto;
import cc.cnfc.entity.ac.Customer;
import cc.cnfc.mapper.CustomerMapper;
import cc.cnfc.service.CustomerService;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustomerService customerSvc;

	/**
	 * 去用户列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toListPage.do")
	public String toListPage() {
		return "customer/wrap_customer_list";
	}

	@RequestMapping(value = "/list.do")
	public @ResponseBody String list() {
		try {
			customerSvc.findLoginCustomer();
			Page page = customerSvc.list();
			return page.getJQGridJsonString();
		} catch (Exception e) {
			logger.error("CustomerController.list error...", e);
			return null;
		}
	}

	// //// test

	@RequestMapping(value = "/find.do")
	public @ResponseBody TestCustomerRoleDto find() {
		try {
			Customer loginCustomer = customerSvc.findLoginCustomer();
			TestCustomerRoleDto customer = customerSvc.find(loginCustomer);
			return customer;
		} catch (Exception e) {
			logger.error("CustomerController.find error...", e);
			return null;
		}
	}

	@RequestMapping(value = "/findByM.do")
	public @ResponseBody Customer findByM(String mSysId) {
		try {
			customerSvc.findLoginCustomer();
			Customer customer = customerSvc.findByM(mSysId);
			return customer;
		} catch (Exception e) {
			logger.error("CustomerController.findByM error...", e);
			return null;
		}
	}

	@RequestMapping(value = "/findLs.do")
	public @ResponseBody List<Customer> findLs(String mSysId) {
		try {
			customerSvc.findLoginCustomer();
			List<Customer> customerLs = customerSvc.findLs();
			return customerLs;
		} catch (Exception e) {
			logger.error("CustomerController.findLs error...", e);
			return null;
		}
	}

	public static void main(String[] args) {
		ApplicationContext ctx = null;
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		CustomerMapper testCustomerDao = (CustomerMapper) ctx.getBean("testCustomerDao");
		// Customer customer = testCustomerDao.findByMSysId("ltuoyb");
		// System.out.println(JSON.toJSONString(customer));
	}

}
