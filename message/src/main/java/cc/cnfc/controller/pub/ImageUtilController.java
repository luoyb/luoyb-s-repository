package cc.cnfc.controller.pub;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cc.cnfc.pub.config.MyProperties;

@Controller
@RequestMapping(value = "/imageUtil")
public class ImageUtilController {

	@Autowired
	private MyProperties myProperties;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private static String IMAGE_POSTFIX = ".jpg";

	/**
	 * 保存url指定的图片到本地
	 * 
	 * @param srcImageUrl
	 * @param destImageName
	 */
	public void saveByUrl(String srcImageUrl, String destImageName) {
		try {
			File destFile = new File(myProperties.getImageDir() + destImageName + IMAGE_POSTFIX);
			if (!destFile.exists()) {
				FileUtils.copyURLToFile(new URL(srcImageUrl), destFile);
			}
		} catch (Exception e) {
			logger.error("DisplayImageController.saveByUrl error...", e);
		}
	}

	/**
	 * 显示图片
	 * 
	 * @param imageName
	 * @param response
	 */
	@RequestMapping(value = "/show.do")
	public void show(String imageName, HttpServletResponse response) {
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			File file = new File(myProperties.getImageDir() + imageName + IMAGE_POSTFIX);
			if (file.exists()) {
				bis = new BufferedInputStream(new FileInputStream(file));
				os = response.getOutputStream();
				response.setHeader("Content-Length", String.valueOf(file.length()));
				IOUtils.copy(bis, os);
			}
		} catch (Exception e) {
			logger.error("DisplayImageController.show error...", e);
		} finally {
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(bis);
		}
	}
}
