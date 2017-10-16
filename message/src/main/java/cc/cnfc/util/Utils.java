/**
 * 
 */
package cc.cnfc.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author luoyb
 *
 */
public class Utils {

	/**
	 * json串转成对象
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T json2Obj(String json, Class<T> clazz) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String urlEncode(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String urlDecode(String str) {
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	public static String collectionToString(Collection<String> coll) {
		if (coll == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		for (String string : coll) {
			if (result.length() != 0) {
				result.append(",");
			}
			result.append(string);
		}
		return result.toString();
	}

	public static String stringsAddPrefix(String strs, String prefix) {
		Set<String> set = new HashSet<String>();
		String[] strArr = strs.split(",");
		for (String str : strArr) {
			set.add(prefix + str);
		}
		return Utils.collectionToString(set);
	}

	/**
	 * 
	 * @param file
	 * @param startRow
	 *            从0开始
	 * @param column
	 *            从0开始
	 * @return
	 * @throws Exception
	 */
	public static String excelFile2Strs(MultipartFile file, int startRow, int column) throws Exception {

		if (file == null || file.getSize() <= 0) {
			return StringUtils.EMPTY;
		}

		Workbook rwb = Workbook.getWorkbook(file.getInputStream());
		Sheet sheet = rwb.getSheet(0);
		Set<String> set = new HashSet<String>();
		for (int i = startRow; i < sheet.getRows(); i++) {
			String cnt = sheet.getCell(column, i).getContents();
			set.add(cnt);
		}
		if (CollectionUtils.isEmpty(set)) {
			return StringUtils.EMPTY;
		}
		return Utils.collectionToString(set);
	}

	public static void main(String[] args) {
		Set<String> set = new HashSet<String>();
		set.add("123");
		set.add("456");
		set.add("789");
		String result = Utils.collectionToString(set);
		System.out.println(result);
	}

}
