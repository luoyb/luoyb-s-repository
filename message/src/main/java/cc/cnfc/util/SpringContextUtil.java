package cc.cnfc.util;

import org.springframework.beans.BeansException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by jiangwh on 2016/7/27.
 */
public class SpringContextUtil {

	private static WebApplicationContext wac;

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanId) {
		T bean = null;
		try {
			bean = (T) getWac().getBean(beanId);
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		T bean = null;
		try {
			bean = (T) getWac().getBeansOfType(clazz);
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	public static synchronized WebApplicationContext getWac() {
		if (wac == null) {
			wac = ContextLoader.getCurrentWebApplicationContext();
		}
		return wac;
	}
}
