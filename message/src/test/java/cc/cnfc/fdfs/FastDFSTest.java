package cc.cnfc.fdfs;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import cc.cnfc.util.FastDFSUtil;

public class FastDFSTest {
	/**
	 * 文件上传测试
	 */
	@Test
	public void testUpload() {
		File file = new File("/Users/luoyb/Downloads/test.png");
		Map<String, String> metaList = new HashMap<String, String>();
		metaList.put("width", "1024");
		metaList.put("height", "768");
		metaList.put("author", "卡卡");
		metaList.put("date", "20171104");
		String fid = FastDFSUtil.uploadFile(file, file.getName(), metaList);
		System.out.println("upload local file " + file.getPath() + " ok, fileid=" + fid);
		// 上传成功返回的文件ID： group1/M00/00/00/wKgAyVgFk9aAB8hwAA-8Q6_7tHw351.jpg
	}

	/**
	 * 文件下载测试
	 */
	public void testDownload() {
		int r = FastDFSUtil.downloadFile("group1/M00/00/00/wKgBaVn9sW2ASY4KACAlVTTyCaY855.png", new File(
				"/Users/luoyb/Downloads/DownloadFile_fid.jpg"));
		System.out.println(r == 0 ? "下载成功" : "下载失败");
	}

	/**
	 * 获取文件元数据测试
	 */
	public void testGetFileMetadata() {
		Map<String, String> metaList = FastDFSUtil
				.getFileMetadata("group1/M00/00/00/wKgBaVn9sW2ASY4KACAlVTTyCaY855.png");
		for (Iterator<Map.Entry<String, String>> iterator = metaList.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, String> entry = iterator.next();
			String name = entry.getKey();
			String value = entry.getValue();
			System.out.println(name + " = " + value);
		}
	}

	/**
	 * 文件删除测试
	 */
	public void testDelete() {
		int r = FastDFSUtil.deleteFile("group1/M00/00/00/wKgBaVn9sW2ASY4KACAlVTTyCaY855.png");
		System.out.println(r == 0 ? "删除成功" : "删除失败");
	}
}
