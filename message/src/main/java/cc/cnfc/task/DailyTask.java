package cc.cnfc.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cc.cnfc.entity.TempSms;
import cc.cnfc.pub.constant.Const.SmsSendChannel;
import cc.cnfc.service.MqService;
import cc.cnfc.service.sms.SmsService;

@Component
public class DailyTask {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SmsService smsSvc;
	@Autowired
	private MqService mqSvc;

	/**
	 * cron格式：[秒] [分] [小时] [日] [月] [周] [年]
	 */
	// @Scheduled(cron = "0/10 * * * * ?")
	public void executeTask() {
		// System.out.println("daily task 任务执行");
	}

	/**
	 * 每25分钟检测一次
	 */
	@Scheduled(cron = "0 */25 * * * ?")
	public void sendSms() {
		logger.info("开始发送临时表里的短信");
		if (smsSvc.time2SendSms()) {
			logger.info("满足发送时间，可以发送");
			List<TempSms> tSmsLs = smsSvc.getAllTempSms();
			for (TempSms tSms : tSmsLs) {
				// 推迟到第二天才发送的短信，改用yc来发
				String mqDtoJson = tSms.getMqDtoJson()
						.replace(SmsSendChannel.NEXMO_MARKETING.toString(), SmsSendChannel.YC_MARKETING.toString())
						.replace(SmsSendChannel.NEXMO_STRICT.toString(), SmsSendChannel.YC.toString());
				mqSvc.enqueue(mqDtoJson);
				smsSvc.deleteTempSms(tSms.getId());
			}
			logger.info("发送临时表里的短信完毕");
		} else {
			logger.info("不是发送的时间段，不发送临时表里的短信");
		}

	}

}
