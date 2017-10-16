package cc.cnfc.controller.sms;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cc.cnfc.core.orm.Page;
import cc.cnfc.dto.Result;
import cc.cnfc.entity.ac.Customer;
import cc.cnfc.pub.constant.Const;
import cc.cnfc.service.CustomerService;
import cc.cnfc.service.MService;
import cc.cnfc.service.sms.SmsBlacklistService;
import cc.cnfc.util.Utils;

@Controller
@RequestMapping(value = "/smsBlacklist")
public class SmsBlacklistController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustomerService customerSvc;
	@Autowired
	private MService mSvc;
	@Autowired
	private SmsBlacklistService smsBlacklistSvc;

	@RequestMapping(value = "/toImportPage.do")
	public String toImportPage() {
		return "sms/blacklist/import/wrap_sms_blacklist_import";
	}

	/**
	 * 去短信黑名单列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toListPage.do")
	public String toListPage() {
		return "sms/blacklist/wrap_sms_blacklist";
	}

	@RequestMapping(value = "/importExcel.do")
	public @ResponseBody Result importExcel(@RequestParam(value = "file", required = false) MultipartFile file,
			String reason) {
		try {
			Customer customer = customerSvc.findLoginCustomer();
			String mobiles = Utils.excelFile2Strs(file, 1, 0);
			smsBlacklistSvc.add(customer.getmSysId(), mobiles, reason);
			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("importExcel error...", e);
			return new Result(Const.NO, e.getMessage());
		}
	}

	@RequestMapping(value = "/list.do")
	public @ResponseBody String list() {
		try {
			Customer customer = customerSvc.findLoginCustomer();
			Page page = smsBlacklistSvc.list(customer.getmSysId());
			return page.getJQGridJsonString();
		} catch (Exception e) {
			logger.error("SmsBlacklistController.list error...", e);
			return null;
		}
	}

	/**
	 * 新增、编辑、删除
	 *
	 * @param httpSession
	 * @param smsTemplate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/oper.do")
	public @ResponseBody void oper(String id, String mobiles, String reason, String oper) throws Exception {
		if ("add".equals(oper)) {
			this.add(mobiles, reason);
		}
		if ("edit".equals(oper)) {
			this.update(Long.valueOf(id), mobiles, reason);
		}
		if ("del".equals(oper)) {
			String[] idArr = id.split(",");
			for (String _id : idArr) {
				if (StringUtils.isNotBlank(_id)) {
					this.delete(Long.valueOf(id));
				}
			}
		}
	}

	/**
	 * 新增模板
	 *
	 * @param httpSession
	 * @param smsTemplate
	 * @return
	 * @throws Exception
	 */
	private void add(String mobiles, String reason) throws Exception {
		Customer customer = customerSvc.findLoginCustomer();
		smsBlacklistSvc.add(customer.getmSysId(), mobiles, reason);
	}

	/**
	 * 编辑模板
	 *
	 * @param httpSession
	 * @param smsTemplate
	 * @return
	 * @throws Exception
	 */
	private void update(Long id, String mobiles, String reason) throws Exception {
		Customer customer = customerSvc.findLoginCustomer();
		smsBlacklistSvc.update(customer.getmSysId(), id, mobiles, reason);
	}

	/**
	 * 删除模版
	 *
	 * @param httpSession
	 * @param id
	 * @return
	 */
	private void delete(Long id) {
		Customer customer = customerSvc.findLoginCustomer();
		smsBlacklistSvc.delete(customer.getmSysId(), id);
	}

}
