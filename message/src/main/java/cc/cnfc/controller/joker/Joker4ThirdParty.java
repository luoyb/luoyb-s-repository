package cc.cnfc.controller.joker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.cnfc.core.orm.Page;
import cc.cnfc.dto.PageResult;
import cc.cnfc.dto.Result;
import cc.cnfc.pub.constant.Const;
import cc.cnfc.service.MService;
import cc.cnfc.service.joker.JokerService;

@Controller
@RequestMapping(value = "/joker4tp")
public class Joker4ThirdParty {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JokerService jokerSvc;
	@Autowired
	private MService mSvc;

	@RequestMapping(value = "/findNewJokers.do")
	public @ResponseBody PageResult findNewJokers(String mSysId,
			String timestamp, String signature, String pageNo) {
		try {
			// mSvc.checkSignature(mSysId, timestamp, signature);
			Page page = jokerSvc.findJokers("createTime desc");
			return new PageResult(Const.YES, page);
		} catch (Exception e) {
			logger.error("findNewJokers error...", e);
			return new PageResult(Const.NO, e.getMessage());
		}
	}

	@RequestMapping(value = "/findHotJokers.do")
	public @ResponseBody PageResult findHotJokers(String mSysId,
			String timestamp, String signature, String pageNo) {
		try {
			// mSvc.checkSignature(mSysId, timestamp, signature);
			Page page = jokerSvc.findJokers("likeCount desc");
			return new PageResult(Const.YES, page);
		} catch (Exception e) {
			logger.error("findHotJokers error...", e);
			return new PageResult(Const.NO, e.getMessage());
		}
	}

	@RequestMapping(value = "/addLikeCount.do")
	public @ResponseBody Result addLikeCount(Long jokerId) {
		try {
			// mSvc.checkSignature(mSysId, timestamp, signature);
			jokerSvc.addLikeCount(jokerId);
			return new Result(Const.YES);
		} catch (Exception e) {
			logger.error("addLikeCount error...", e);
			return new Result(Const.NO, e.getMessage());
		}
	}

}
