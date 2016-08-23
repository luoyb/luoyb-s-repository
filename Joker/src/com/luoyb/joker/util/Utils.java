package com.luoyb.joker.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

public class Utils {

	/**
	 * 时间戳转换成时间字符串
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String timestamp2Datetime(String timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = sdf.format(new Date(Long.parseLong(timestamp)));
		return datetime;
	}

}
