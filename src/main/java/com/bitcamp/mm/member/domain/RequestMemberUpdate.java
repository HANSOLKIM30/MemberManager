package com.bitcamp.mm.member.domain;

import org.springframework.web.multipart.MultipartFile;

public class RequestMemberUpdate {

	private int idx;
	private String uId;
	private String uPW;
	private String uName;
	private MultipartFile newFile;
	private String oldFile;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getuPW() {
		return uPW;
	}
	public void setuPW(String uPW) {
		this.uPW = uPW;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public MultipartFile getNewFile() {
		return newFile;
	}
	public void setNewFile(MultipartFile newFile) {
		this.newFile = newFile;
	}
	public String getOldFile() {
		return oldFile;
	}
	public void setOldFile(String oldFile) {
		this.oldFile = oldFile;
	}
	
	public MemberInfo toMemberInfo() {
		
		MemberInfo memberInfo = new MemberInfo();
		
		memberInfo.setIdx(idx);
		memberInfo.setuId(uId);
		memberInfo.setuPW(uPW);
		memberInfo.setuName(uName);
		
		return memberInfo;
	}
	
	@Override
	public String toString() {
		return "RequestMemberUpdate [idx=" + idx + ", uId=" + uId + ", uPW=" + uPW + ", uName=" + uName + ", newFile="
				+ newFile + ", oldFile=" + oldFile + "]";
	}
	
	
}
