/**
 * @(#)StringUtil.java 2010-7-30
 * 
 *                     Copyright 2000-2010 by RENWOYOU Corporation.
 *
 *                     All rights reserved.
 *
 *                     This software is the confidential and proprietary information of RENWOYOU Corporation ("Confidential Information"). You shall not disclose such Confidential Information and
 *                     shall use it only in accordance with the terms of the license agreement you entered into with RENWOYOU.
 * 
 */

package cc.cnfc.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * 字符串相关工具类，首先应该确认 apache commons-lang有没有想要的方法
 * 
 * @author
 * @date 2010-7-30
 * @version $Revision$
 */
public class StringUtil {

	private static Pattern duplicateBlank = Pattern.compile(" {2,}");

	/**
	 * 按指定长度，分隔字符串
	 * 
	 * @param msg
	 * @param num
	 * @return
	 */
	public static String[] split(String msg, int num) {
		int len = msg.length();
		if (len <= num) {
			return new String[] { msg };
		}
		int count = len / (num - 1);
		count += len > (num - 1) * count ? 1 : 0;
		String[] result = new String[count];
		int pos = 0;
		int splitLen = num - 1;
		for (int i = 0; i < count; i++) {
			if (i == count - 1) {
				splitLen = len - pos;
			}
			result[i] = msg.substring(pos, pos + splitLen);
			pos += splitLen;
		}
		return result;
	}

	/**
	 * 转换成sql in的查询条件
	 * 
	 * @param object
	 * @return
	 */
	public static String getInString(Object object) {
		Assert.notNull(object, "object不能为空");
		StringBuffer bf = new StringBuffer();
		String str = object.toString();
		Assert.hasLength(str, "str不能为空");
		if (str.indexOf(",") == -1) {
			return "('" + str + "')";
		}
		String[] ips = str.split(",");
		for (int i = 0; i < ips.length; i++) {
			String ip = ips[i];
			if (StringUtils.isEmpty(ip)) {
				continue;
			}
			if (i == ips.length - 1) {
				bf.append("'" + ip + "'");
			} else {
				bf.append("'" + ip + "',");
			}
		}
		return "(" + bf.toString() + ")";
	}

	/**
	 * 去除字符串里的重复空格及前后空格
	 * 
	 * @param source
	 * @return
	 */
	public static String removeDuplicateBlank(String source) {
		if (source == null) {
			return source;
		}
		Matcher m = duplicateBlank.matcher(source.trim());
		String result = m.replaceAll(" ");
		return result;
	}

	/**
	 * 根据分隔符，分割str
	 * 
	 * @param str
	 * @param split
	 * @return
	 */
	// public static List<String> split2List(String str, String split) {
	// if (StringUtils.isBlank(str)) {
	// return null;
	// }
	// return Lists.newArrayList(Splitter.on(split).omitEmptyStrings()
	// .trimResults().split(str));
	// }

	/**
	 * 删除重复的中文符号
	 * 
	 * @param str
	 * @return
	 */
	public static String removeDuplicatedChnSymbol(String str) {
		str = StringUtils.replace(str, "，，", "，");
		str = StringUtils.replace(str, "，。", "，");
		str = StringUtils.replace(str, "。，", "，");
		str = StringUtils.replace(str, "。。", "，");
		str = StringUtils.replace(str, "；，", "，");
		return str;
	}

	/**
	 * 先trimToEmtpy，再删除str的空格，并在两边添加%
	 * 
	 * @param str
	 * @return
	 */
	public static String getDbLikeStr(String str) {
		str = StringUtils.trimToEmpty(str);
		if (StringUtils.EMPTY.equals(str)) {
			return null;
		}
		return "%".concat(StringUtils.deleteWhitespace(str)).concat("%");
	}

	public static void main(String[] args) {
		System.out.println(removeDuplicatedChnSymbol("，。"));
		System.out.println(getDbLikeStr("kk  k   kk"));
	}
}
