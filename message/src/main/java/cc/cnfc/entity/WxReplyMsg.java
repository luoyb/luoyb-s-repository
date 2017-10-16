package cc.cnfc.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cc.cnfc.core.entity.IdEntity;

/**
 * 被动回复消息， mSysId、msgType、keyWord构成唯一性
 * 
 * @author luoyb
 *
 */
@Entity
@Table(name = "m_wx_reply_msg")
public class WxReplyMsg extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -699069751000219107L;

	private String mSysId;
	private String msgType; // 请求事件类型，text、SCAN、CLICK、VIEW、subscribe、unsubscribe、MASSSENDJOBFINISH
	private String replyType; // 回复类型，text、image、voice、video、music、news
	private String keyWord; // 关键字，default、....

	private String name;
	private String matchRule; // 对关键字是否全部匹配或部分匹配，ALL、PART
	// 回复文本或图片、语音、视频的mediaId
	private String content; // 文本内容

	// // 回复图片、语音、视频
	// private String mediaId;// 多媒体id

	// // 回复图文
	// private String title; // 音乐/图文标题
	// private String description; // 音乐/图文描述
	// private String picUrl;// 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
	// private String url;// 点击图文消息跳转链接
	// // 回复音乐
	// private String musicURL; // 音乐链接
	// private String hQMusicUrl; // 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	// private String thumbMediaId;// 缩略图的媒体id，通过素材管理接口上传多媒体文件，得到的id

	public String getmSysId() {
		return mSysId;
	}

	public void setmSysId(String mSysId) {
		this.mSysId = mSysId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	// public String getMediaId() {
	// return mediaId;
	// }
	//
	// public void setMediaId(String mediaId) {
	// this.mediaId = mediaId;
	// }

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getReplyType() {
		return replyType;
	}

	public void setReplyType(String replyType) {
		this.replyType = replyType;
	}

	public String getMatchRule() {
		return matchRule;
	}

	public void setMatchRule(String matchRule) {
		this.matchRule = matchRule;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
