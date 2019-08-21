package com.bitcamp.mm.member.dao;

import java.util.List;
import java.util.Map;

import com.bitcamp.mm.member.domain.MemberInfo;
import com.bitcamp.mm.member.domain.SearchParam;

public interface MemberSessionDao {

	public int insertMember(MemberInfo memberInfo);
	
	public int updateMember(MemberInfo memberInfo);
	
	public int deleteMember(String uId);
	
	public int memberDeleteManager(int idx);
	
	public int selectTotalCount(SearchParam searchParam);
	
	public MemberInfo selectMemberById(String uId);
	
	public MemberInfo selectMemberByIdx(int idx);
	
	public List<MemberInfo> selectList(Map<String, Object> params);
	
	public int updateVerify(String uId, String code);
	//아래 메서드는 모두 selectList를 통해 동적쿼리로 처리
	/*
	 * public List<MemberInfo> selectListByBoth(Map<String, Object> params);
	 * 
	 * public List<MemberInfo> selectListByName(Map<String, Object> params);
	 * 
	 * public List<MemberInfo> selectListById(Map<String, Object> params);
	 */
	
	// 2019.08.14 추가
	// 회원의 전체 리스트
	public List<MemberInfo> selectAllList();
}
