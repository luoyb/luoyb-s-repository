/**
 * @(#)ExceptionUtil.java 2010-1-13
 * 
 * Copyright (c) 2000-2010 by RENWOYOU Corporation.
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * RENWOYOU Corporation ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with RENWOYOU.
 * 
 */

package cc.cnfc.core.exception;

import java.lang.reflect.InvocationTargetException;

/**
 * 异常工具类
 * 
 * @author
 * @date 2010-1-13
 * @version $Revision$
 */
public class ExceptionUtil {

	/**
	 * 将checked exception转换为unchecked exception.
	 * 这个方法通常用于service层捕捉checked异常而转换，进而触发回滚
	 */
	public static RuntimeException convertCheckedExceptionToUnchecked(
			Exception e) {
		if (e instanceof IllegalAccessException
				|| e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException)
			return new IllegalArgumentException("Reflection Exception.", e);
		else if (e instanceof InvocationTargetException) {
			Throwable target = ((InvocationTargetException) e)
					.getTargetException();
			if (target instanceof RuntimeException) {
				return (RuntimeException) target;
			}
			return new GenericException("Invocation Target Exception.", target);
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new GenericException("Unexpected Checked Exception.", e);
	}

}
