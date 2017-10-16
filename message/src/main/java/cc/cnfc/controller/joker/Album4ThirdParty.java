package cc.cnfc.controller.joker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.cnfc.core.orm.Page;
import cc.cnfc.dto.PageResult;
import cc.cnfc.pub.constant.Const;
import cc.cnfc.service.MService;
import cc.cnfc.service.joker.AlbumService;

@Controller
@RequestMapping(value = "/album4tp")
public class Album4ThirdParty {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AlbumService albumSvc;
	@Autowired
	private MService mSvc;

	@RequestMapping(value = "/findNewAlbums.do")
	public @ResponseBody PageResult findNewAlbums(String mSysId,
			String timestamp, String signature, String pageNo) {
		try {
			// mSvc.checkSignature(mSysId, timestamp, signature);
			Page page = albumSvc.findAlbums("createTime desc");
			return new PageResult(Const.YES, page);
		} catch (Exception e) {
			logger.error("findNewAlbums error...", e);
			return new PageResult(Const.NO, e.getMessage());
		}
	}

	@RequestMapping(value = "/albumDetail.do")
	public @ResponseBody PageResult albumDetail(String mSysId,
			String timestamp, String signature, Long albumId) {
		try {
			// mSvc.checkSignature(mSysId, timestamp, signature);
			Page page = albumSvc.albumDetail(albumId);
			return new PageResult(Const.YES, page);
		} catch (Exception e) {
			logger.error("albumDetail error...", e);
			return new PageResult(Const.NO, e.getMessage());
		}
	}

}
