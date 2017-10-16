/**
 * @(#)GenericException 2009-12-8
 * 
 * Copyright 2000-2009 by RENWOYOU Corporation.
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

/**
 * 系统通用的受查异常，适合用于统一装载错误信息显示给最终用户
 * 
 * @author 		denny
 * @version 	1.0
 */
public class GenericException extends RuntimeException {

	private static final long serialVersionUID = 1300705338886707272L;

	protected String errorMessage = "";
	protected String fullMessage = "";
	protected String[] args = null;

	public GenericException() {
		super();
		handle();
	}

	public GenericException(String message) {
		super(message);
		this.errorMessage = message;
		handle();
	}

	public GenericException(String message, String arg1) {
		super(message);
		this.errorMessage = message;
		this.args = new String[] { arg1 };
		handle();
	}

	public GenericException(String message, String arg1, String arg2) {
		super(message);
		this.errorMessage = message;
		this.args = new String[] { arg1, arg2 };
		handle();
	}

	public GenericException(String message, String args[]) {
		super(message);
		this.errorMessage = message;
		this.args = args;
		handle();
	}

	public GenericException(Throwable t) {
		super(t);
		handle();
	}

	public GenericException(String message, Throwable t) {
		super(message, t);
		this.errorMessage = message;
		handle();
	}

	public GenericException(String message, String arg1, Throwable t) {
		super(message, t);
		this.errorMessage = message;
		this.args = new String[] { arg1 };
		handle();
	}

	public GenericException(String message, String arg1, String arg2, Throwable t) {
		super(message, t);
		this.errorMessage = message;
		this.args = new String[] { arg1, arg2 };
		handle();
	}

	public GenericException(String errorMessage, String args[], Throwable t) {
		super(errorMessage, t);
		this.errorMessage = errorMessage;
		this.args = args;
		handle();
	}

	/**
	 */
	public String getMessage() {
		return this.errorMessage;
	}

	/**
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}

	public String[] getArgs() {
		return this.args;
	}

	/**
	 */
	public String getStackTraceMessage() {

		return this.makeStackTraceMessage();
	}

	/**
	 * 构造出错的堆栈信息
	 * @return 堆栈信息
	 */
	private String makeStackTraceMessage() {
		StackTraceElement[] traces = this.getStackTrace();
		//如果是由RuntimeException引起的则定位到RuntimeException
		if (this.getCause() != null && this.getCause() instanceof RuntimeException) {
			traces = this.getCause().getStackTrace();
		}
		StringBuffer sb = new StringBuffer("");
		if (traces != null && traces.length > 0) {
			sb.append(traces[0].getClassName());
			sb.append(".");
			sb.append(traces[0].getMethodName());
			sb.append(":");
			sb.append(traces[0].getLineNumber());
		}
		return sb.toString();

	}

	private void handle() {
		StringBuffer sb = new StringBuffer("");
		sb.append(this.errorMessage);
		sb.append("\n");
		sb.append(this.makeStackTraceMessage());
		this.fullMessage = sb.toString();

	}

}
