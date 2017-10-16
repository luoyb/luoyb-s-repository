package cc.cnfc.controller.sms;

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
import cc.cnfc.dto.sms.SmsDto;
import cc.cnfc.entity.ac.Customer;
import cc.cnfc.pub.constant.Const;
import cc.cnfc.pub.constant.Const.SmsSendChannel;
import cc.cnfc.pub.constant.Const.SmsType;
import cc.cnfc.service.CustomerService;
import cc.cnfc.service.MService;
import cc.cnfc.service.sms.SmsService;
import cc.cnfc.util.Utils;

@Controller
@RequestMapping(value = "/sms")
public class SmsController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CustomerService customerSvc;
	@Autowired
	private MService mSvc;
	@Autowired
	private SmsService smsSvc;

	@RequestMapping(value = "/toSendSms.do")
	public String toSendSms() {
		return "sms/send/wrap_send_sms";
	}

	@RequestMapping(value = "/sendSms.do")
	public @ResponseBody Result sendSms(@RequestParam(value = "file", required = false) MultipartFile file,
			SmsDto sDto, String mobileSource) {
		try {
			Customer customer = customerSvc.findLoginCustomer();

			if (SmsType.BIZ.toString().equals(sDto.getSmsType())) {
				sDto.setSendChannel(SmsSendChannel.NEXMO_STRICT.toString());
			}
			if (SmsType.MARKETING.toString().equals(sDto.getSmsType())) {
				sDto.setSendChannel(SmsSendChannel.NEXMO_MARKETING.toString());
			}

			if (Const.MobileSource.EXCEL.toString().equals(mobileSource)) {
				String mobiles = Utils.excelFile2Strs(file, 1, 0);
				sDto.setMobile(mobiles);
			}
			if (Const.MobileSource.REGISTER.toString().equals(mobileSource)) {
				sDto.setIsGroupSend(Const.YES);
			}

			smsSvc.enqueueSms(customer.getmSysId(), sDto);
			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("sendSms error...", e);
			return new Result(Const.NO, e.getMessage());
		}
	}

	@RequestMapping(value = "/toSmsSendHistPage.do")
	public String toSmsSendHistPage() {
		return "sms/hist/wrap_send_hist";
	}

	@RequestMapping(value = "/smsSendHist.do")
	public @ResponseBody String smsSendHist() {
		try {
			Customer customer = customerSvc.findLoginCustomer();
			Page page = smsSvc.findSmsLogByMSysId(customer.getmSysId());
			return page.getJQGridJsonString();
		} catch (Exception e) {
			logger.error("smsSendHist error...", e);
			return null;
		}
	}

}
