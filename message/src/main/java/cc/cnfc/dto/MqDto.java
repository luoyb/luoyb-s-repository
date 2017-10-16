/**
 * 
 */
package cc.cnfc.dto;

import java.util.Map;

import cc.cnfc.pub.constant.Const.MqMsgType;

/**
 * @author luoyb
 *
 */
public class MqDto<T> {

	private String mSysId; // 第三方系统标示
	private MqMsgType msgType;// 消息类型，比如：微信消息、短信消息
	private T msgObject; // 具体消息对象
	private Map<String, Object> extra;

	public MqDto() {
	}

	public MqDto(String mSysId, MqMsgType msgTpye, T msgObject) {
		this.mSysId = mSysId;
		this.msgType = msgTpye;
		this.msgObject = msgObject;
	}

	public MqDto(String mSysId, MqMsgType msgTpye, T msgObject,
			Map<String, Object> extra) {
		this.mSysId = mSysId;
		this.msgType = msgTpye;
		this.msgObject = msgObject;
		this.extra = extra;
	}

	public MqMsgType getMsgType() {
		return msgType;
	}

	public void setMsgType(MqMsgType msgType) {
		this.msgType = msgType;
	}

	public T getMsgObject() {
		return msgObject;
	}

	public void setMsgObject(T msgObject) {
		this.msgObject = msgObject;
	}

	public String getmSysId() {
		return mSysId;
	}

	public void setmSysId(String mSysId) {
		this.mSysId = mSysId;
	}

	public Map<String, Object> getExtra() {
		return extra;
	}

	public void setExtra(Map<String, Object> extra) {
		this.extra = extra;
	}

}
