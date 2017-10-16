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
import cc.cnfc.service.joker.FilmService;

@Controller
@RequestMapping(value = "/film4tp")
public class Film4ThirdParty {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FilmService filmSvc;
	@Autowired
	private MService mSvc;

	@RequestMapping(value = "/findNewFilms.do")
	public @ResponseBody PageResult findNewFilms(String mSysId,
			String timestamp, String signature, String pageNo) {
		try {
			// mSvc.checkSignature(mSysId, timestamp, signature);
			Page page = filmSvc.findFilms("createTime desc");
			return new PageResult(Const.YES, page);
		} catch (Exception e) {
			logger.error("findNewFilms error...", e);
			return new PageResult(Const.NO, e.getMessage());
		}
	}

	@RequestMapping(value = "/findFilmComment.do")
	public @ResponseBody PageResult findFilmComment(String mSysId,
			String timestamp, String signature, Long filmId, String pageNo) {
		try {
			// mSvc.checkSignature(mSysId, timestamp, signature);
			Page page = filmSvc.findFilmComment(filmId);
			return new PageResult(Const.YES, page);
		} catch (Exception e) {
			logger.error("findFilmComment error...", e);
			return new PageResult(Const.NO, e.getMessage());
		}
	}

}
