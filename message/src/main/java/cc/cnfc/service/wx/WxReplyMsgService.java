/**
 * 
 */
package cc.cnfc.service.wx;

import java.util.List;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.cnfc.core.orm.Page;
import cc.cnfc.core.orm.hibernate.HibernatePage;
import cc.cnfc.core.service.BaseService;
import cc.cnfc.dao.WxReplyMsgDao;
import cc.cnfc.entity.WxReplyMsg;

/**
 * @author luoyb
 *
 */
@Service
@Transactional
public class WxReplyMsgService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4659914126325365007L;

	public static final String DEFAULT_KEY_WORD = "default";

	public static final String MATCH_RULE_ALL = "ALL";
	public static final String MATCH_RULE_PART = "PART";

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WxReplyMsgDao wxReplyMsgDao;

	public Page textKeywordReplyList(String mSysId) {
		HibernatePage hibernatePage = new HibernatePage();
		hibernatePage.setQueryClass(WxReplyMsg.class);
		hibernatePage.setConditionEqual("mSysId", mSysId);
		hibernatePage.setConditionEqual("msgType", WxConsts.XML_MSG_TEXT);
		hibernatePage.setConditionNotEqual("keyWord", DEFAULT_KEY_WORD);
		hibernatePage.setOrderBy("id desc");
		Page page = this.query4JqGrid(hibernatePage);
		return page;
	}

	/**
	 * 更新文本关键字回复
	 * 
	 * @param id
	 * @param mSysId
	 * @param name
	 * @param keyword
	 * @param replyType
	 * @param content
	 */
	public void updateTextKeyWordReply(Long id, String mSysId, String name, String keyWord, String replyType,
			String content) {
		// 删除旧的记录
		WxReplyMsg oldReplyMsg = this.findUniqueReply(mSysId, WxConsts.XML_MSG_TEXT, keyWord);
		if (oldReplyMsg != null) {
			this.delete(oldReplyMsg);
		}
		// 添加新的记录
		WxReplyMsg replyMsg = this.load(WxReplyMsg.class, id);
		replyMsg = (replyMsg == null ? new WxReplyMsg() : replyMsg);
		replyMsg.setmSysId(mSysId);
		replyMsg.setName(name);
		replyMsg.setMsgType(WxConsts.XML_MSG_TEXT);
		replyMsg.setKeyWord(keyWord);
		replyMsg.setContent(content);
		replyMsg.setReplyType(replyType);
		wxReplyMsgDao.save(replyMsg);
	}

	/**
	 * 保存菜单点击事件的回复内容
	 * 
	 * @param mSysId
	 * @param name
	 * @param keyWord
	 * @param replyType
	 * @param content
	 */
	public void saveMenuClickReply(String mSysId, String name, String keyWord, String replyType, String content) {
		// 删除旧的记录
		WxReplyMsg oldReplyMsg = this.findUniqueReply(mSysId, WxConsts.EVT_CLICK, keyWord);
		if (oldReplyMsg != null) {
			this.delete(oldReplyMsg);
		}
		// 添加新的记录
		WxReplyMsg replyMsg = new WxReplyMsg();
		replyMsg.setmSysId(mSysId);
		replyMsg.setName(name);
		replyMsg.setMsgType(WxConsts.EVT_CLICK);
		replyMsg.setKeyWord(keyWord);
		replyMsg.setContent(content);
		replyMsg.setReplyType(replyType);
		this.add(replyMsg);
	}

	public void saveTextKeyWordReply(String mSysId, String name, String keyWord, String replyType, String content) {
		// 删除旧的记录
		WxReplyMsg oldReplyMsg = this.findUniqueReply(mSysId, WxConsts.XML_MSG_TEXT, keyWord);
		if (oldReplyMsg != null) {
			this.delete(oldReplyMsg);
		}
		// 添加新的记录
		WxReplyMsg replyMsg = new WxReplyMsg();
		replyMsg.setmSysId(mSysId);
		replyMsg.setName(name);
		replyMsg.setMsgType(WxConsts.XML_MSG_TEXT);
		replyMsg.setKeyWord(keyWord);
		replyMsg.setContent(content);
		replyMsg.setReplyType(replyType);
		this.add(replyMsg);
	}

	public void saveTextDefaultReply(String mSysId, WxReplyMsg replyMsg) {
		// 删除旧的记录
		WxReplyMsg oldReplyMsg = this.findDefaultReply(mSysId, WxConsts.XML_MSG_TEXT);
		if (oldReplyMsg != null) {
			this.delete(oldReplyMsg);
		}
		// 添加新的记录
		replyMsg.setmSysId(mSysId);
		replyMsg.setMsgType(WxConsts.XML_MSG_TEXT);
		replyMsg.setKeyWord(WxReplyMsgService.DEFAULT_KEY_WORD);
		this.add(replyMsg);
	}

	/**
	 * 保存订阅的回复消息
	 * 
	 * @param mSysId
	 * @param replyMsg
	 */
	public void saveSubscribeReply(String mSysId, WxReplyMsg replyMsg) {
		// 删除旧的记录
		WxReplyMsg oldReplyMsg = this.findDefaultReply(mSysId, WxConsts.EVT_SUBSCRIBE);
		if (oldReplyMsg != null) {
			this.delete(oldReplyMsg);
		}
		// 添加新的记录
		replyMsg.setmSysId(mSysId);
		replyMsg.setMsgType(WxConsts.EVT_SUBSCRIBE);
		replyMsg.setKeyWord(WxReplyMsgService.DEFAULT_KEY_WORD);
		this.add(replyMsg);
	}

	public List<WxReplyMsg> find(String mSysId, String msgType, String replyType, String content) {
		List<WxReplyMsg> replyMsgLs = wxReplyMsgDao.find(
				"from WxReplyMsg where mSysId = ? and msgType = ? and replyType = ? and content = ?", mSysId, msgType,
				replyType, content);
		return replyMsgLs;
	}

	/**
	 * 通过mSysId和消息类型找到唯一的回复内容
	 * 
	 * @param mSysId
	 * @param msgType
	 * @param keyWord
	 * @return
	 */
	public WxReplyMsg findUniqueReply(String mSysId, String msgType, String keyWord) {
		keyWord = StringUtils.isBlank(keyWord) ? WxReplyMsgService.DEFAULT_KEY_WORD : keyWord;
		return wxReplyMsgDao.findUnique("from WxReplyMsg where mSysId = ? and msgType = ? and keyWord = ?", mSysId,
				msgType, keyWord);
	}

	public List<WxReplyMsg> find(String mSysId, String msgType) {
		return wxReplyMsgDao.find("from WxReplyMsg where mSysId = ? and msgType = ?", mSysId, msgType);
	}

	/**
	 * 通过mSysId和消息类型找到默认的回复内容
	 * 
	 * @param mSysId
	 * @param msgType
	 * @return
	 */
	public WxReplyMsg findDefaultReply(String mSysId, String msgType) {
		return this.findUniqueReply(mSysId, msgType, WxReplyMsgService.DEFAULT_KEY_WORD);
	}

}
