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
import cc.cnfc.service.joker.MusicService;

@Controller
@RequestMapping(value = "/music4tp")
public class Music4ThirdParty {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MusicService musicSvc;
	@Autowired
	private MService mSvc;

	@RequestMapping(value = "/findNewMusics.do")
	public @ResponseBody PageResult findNewMusics(String mSysId,
			String timestamp, String signature, String pageNo) {
		try {
			// mSvc.checkSignature(mSysId, timestamp, signature);
			Page page = musicSvc.findMusics("createTime desc");
			return new PageResult(Const.YES, page);
		} catch (Exception e) {
			logger.error("findNewMusics error...", e);
			return new PageResult(Const.NO, e.getMessage());
		}
	}

	@RequestMapping(value = "/findMusicComment.do")
	public @ResponseBody PageResult findMusicComment(String mSysId,
			String timestamp, String signature, Long musicId, String pageNo) {
		try {
			// mSvc.checkSignature(mSysId, timestamp, signature);
			Page page = musicSvc.findMusicComment(musicId);
			return new PageResult(Const.YES, page);
		} catch (Exception e) {
			logger.error("findMusicComment error...", e);
			return new PageResult(Const.NO, e.getMessage());
		}
	}

}
