package cc.cnfc.dto;

import java.util.List;

/**
 * @author luoyb
 * @date May 19, 2016
 * @version $Revision$
 */
public class MemberInfoResult extends Result {

	private List<MemberInfo> memberArray;

	public List<MemberInfo> getMemberArray() {
		return memberArray;
	}

	public void setMemberArray(List<MemberInfo> memberArray) {
		this.memberArray = memberArray;
	}

}
