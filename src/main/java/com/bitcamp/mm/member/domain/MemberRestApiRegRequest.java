package com.bitcamp.mm.member.domain;

public class MemberRestApiRegRequest {
	
	private String uId;
	private String uName;
	private String uPW;
	
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuPW() {
		return uPW;
	}
	public void setuPW(String uPW) {
		this.uPW = uPW;
	}
	
	@Override
	public String toString() {
		return "MemberRestApiRegRequest [uId=" + uId + ", uName=" + uName + ", uPW=" + uPW + "]";
	}

	public MemberInfo toMemberInfo() {
		
		MemberInfo member = new MemberInfo();
		member.setuId(uId);
		member.setuName(uName);
		member.setuPW(uPW);
		
		return member;
	}
	
	
}
