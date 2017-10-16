package cc.cnfc.dto;

import org.apache.commons.lang.StringUtils;

import cc.cnfc.pub.constant.Const;

/**
 * 接口响应结果
 * 
 * @author luoyb
 * @date May 18, 2016
 * @version $Revision$
 */
public class Result {
	protected String code;
	protected String message;

	public Result() {
	}

	public Result(String code) {
		this.code = Const.YES;
		this.message = StringUtils.EMPTY;
	}

	public Result(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
