package com.luoyb.ad;

/**
 * 线程相关工具类.
 * 
 * @author calvin
 */
public class ThreadUtils {

	private ThreadUtils() {
	}

	/**
	 * sleep等待,单位毫秒,忽略InterruptedException.
	 */
	public static void sleep(long millis) {
		try {
			// System.out.println("sleep " + millis + " millis");
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// Ignore.
		}
	}

	/**
	 * sleep0到1秒内的随机时间
	 */
	public static void randomSleep() {
		ThreadUtils.sleep(Math.round(Math.random() * 1000));
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			ThreadUtils.randomSleep();
			System.out.println(1);
		}

		// System.out.println(Math.round(Math.random()));
	}
}
