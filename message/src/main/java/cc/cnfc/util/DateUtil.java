package cc.cnfc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private final static SimpleDateFormat sdfHour = new SimpleDateFormat("H");

	/**
	 * 获取当前的小时,前面不加0
	 * 
	 * @return
	 */
	public static Integer getCurHour() {
		return Integer.valueOf(sdfHour.format(new Date()));
	}

	public static void main(String[] args) {
		Integer s = DateUtil.getCurHour();
		System.out.println(s);

	}
}
