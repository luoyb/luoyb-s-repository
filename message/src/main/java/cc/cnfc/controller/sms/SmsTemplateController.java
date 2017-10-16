package cc.cnfc.controller.sms;

import me.chanjar.weixin.common.util.StringUtils;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.cnfc.core.orm.Page;
import cc.cnfc.dto.Result;
import cc.cnfc.entity.SmsTemplate;
import cc.cnfc.entity.ac.Customer;
import cc.cnfc.pub.constant.Const;
import cc.cnfc.pub.constant.Const.SmsTemplateStatus;
import cc.cnfc.service.CustomerService;
import cc.cnfc.service.MService;
import cc.cnfc.service.sms.SmsTemplateService;

@Controller
@RequestMapping(value = "/smsTemplate")
public class SmsTemplateController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustomerService customerSvc;
	@Autowired
	private MService mSvc;
	@Autowired
	private SmsTemplateService smsTemplateSvc;

	/**
	 * 去模版列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toListPage.do")
	public String toListPage() {
		return "sms/template/wrap_sms_temp_list";
	}

	/**
	 * 去待审核模板列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toWaitVerifyListPage.do")
	public String toWaitVerifyListPage() {
		return "sms/template/verify/wrap_sms_temp_verify_list";
	}

	@RequestMapping(value = "/waitVerifyList.do")
	public @ResponseBody String waitVerifyList() {
		try {
			SecurityUtils.getSubject().checkPermission("sms-template:verify");

			Page page = smsTemplateSvc.waitVerifyList();
			return page.getJQGridJsonString();
		} catch (Exception e) {
			logger.error("smsTemplateController.waitVerifyList error...", e);
			return null;
		}
	}

	@RequestMapping(value = "/list.do")
	public @ResponseBody String list() {
		try {
			Customer customer = customerSvc.findLoginCustomer();
			Page page = smsTemplateSvc.list(customer.getmSysId());
			return page.getJQGridJsonString();
		} catch (Exception e) {
			logger.error("smsTemplateController.list error...", e);
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
	public @ResponseBody void oper(String id, String templateKey, String templateName, String templateCnt, String oper)
			throws Exception {
		if ("add".equals(oper)) {
			this.newTemplate(templateKey, templateName, templateCnt);
		}
		if ("edit".equals(oper)) {
			this.update(templateKey, templateName, templateCnt);
		}
		if ("del".equals(oper)) {
			String[] idArr = id.split(",");
			for (String _id : idArr) {
				if (StringUtils.isNotBlank(_id)) {
					SmsTemplate st = smsTemplateSvc.load(SmsTemplate.class, Long.valueOf(_id));
					this.delete(st.getmSysId(), st.getTemplateKey());
				}
			}
		}
	}

	/**
	 * 新增模板（进入审核中状态）
	 * 
	 * @param httpSession
	 * @param smsTemplate
	 * @return
	 * @throws Exception
	 */
	private void newTemplate(String templateKey, String templateName, String templateCnt) throws Exception {
		Customer customer = customerSvc.findLoginCustomer();
		SmsTemplate template = smsTemplateSvc.getLatestTemplate(customer.getmSysId(), templateKey);
		if (template != null) {
			throw new RuntimeException("该模板KEY已存在！");
		}
		smsTemplateSvc.saveTemplate(customer.getmSysId(), templateKey, templateName, templateCnt,
				SmsTemplateStatus.APPLYING.toString());
	}

	/**
	 * 编辑模板（进入审核中状态）
	 * 
	 * @param httpSession
	 * @param smsTemplate
	 * @return
	 * @throws Exception
	 */
	private void update(String templateKey, String templateName, String templateCnt) throws Exception {
		Customer customer = customerSvc.findLoginCustomer();
		smsTemplateSvc.saveTemplate(customer.getmSysId(), templateKey, templateName, templateCnt,
				SmsTemplateStatus.APPLYING.toString());
	}

	/**
	 * 删除模版
	 * 
	 * @param httpSession
	 * @param id
	 * @return
	 */
	private void delete(String mSysId, String templateKey) {
		customerSvc.findLoginCustomer();
		smsTemplateSvc.delete(mSysId, templateKey);
	}

	/**
	 * 审核模板
	 * 
	 * @param templateKey
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/verify.do")
	public @ResponseBody Result verify(String mSysId, String templateKey, String status) {
		try {
			Customer customer = customerSvc.findLoginCustomer();
			smsTemplateSvc.verify(mSysId, templateKey, status);
			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("smsTemplateController.verify error...", e);
			return new Result(Const.NO, e.getMessage());
		}
	}

	// @RequestMapping(value = "/apply.do")
	// public @ResponseBody Result apply(HttpSession httpSession, SmsTemplate smsTemplate) {
	// try {
	// Customer customer = customerSvc.findLoginCustomer(httpSession);
	// smsTemplateSvc.applyTemplate(customer.getmSysId(), smsTemplate.getTemplateKey(),
	// smsTemplate.getTemplateName(), smsTemplate.getTemplateCnt(), smsTemplate.getStatus());
	// return new Result(Const.YES);
	// } catch (Exception e) {
	// logger.error("smsTemplateController.apply error...", e);
	// return new Result(Const.NO, e.getMessage());
	// }
	// }

	// @RequestMapping(value = "/passTemplate.do")
	// public @ResponseBody Result passTemplate(String templateKey) {
	// try {
	// Customer customer = customerSvc.findLoginCustomer();
	// smsTemplateSvc.applyTemplate(customer.getmSysId(), templateKey, SmsTemplateStatus.PASS.toString());
	// return new Result(Const.YES);
	// } catch (Exception e) {
	// logger.error("smsTemplateController.passTemplate error...", e);
	// return new Result(Const.NO, e.getMessage());
	// }
	// }
	//
	// @RequestMapping(value = "/refundTemplate.do")
	// public @ResponseBody Result refundTemplate(String templateKey) {
	// try {
	// Customer customer = customerSvc.findLoginCustomer();
	// smsTemplateSvc.applyTemplate(customer.getmSysId(), templateKey, SmsTemplateStatus.REFUSE.toString());
	// return new Result(Const.YES);
	// } catch (Exception e) {
	// logger.error("smsTemplateController.refundTemplate error...", e);
	// return new Result(Const.NO, e.getMessage());
	// }
	// }

}
