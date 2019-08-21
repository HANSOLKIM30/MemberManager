package com.bitcamp.mm.member.service;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitcamp.mm.member.dao.MemberSessionDao;
import com.bitcamp.mm.member.domain.MemberInfo;

@Service("loginService")
public class MemberLoginService implements MemberService {
	/*
	2019.08.21 수정 
	수정 내용  : 인증 상태를 3개 상태로 구분 return, return type 변경
			   boolean -> int
		 	  	0 - 로그인 실패
				1 - 미인증 계정 로그인	
				2 - 정상 로그인
*/
	//@Autowired private MemberJdbcTemplateDao dao;

	@Autowired
	private SqlSessionTemplate template;
	
	private MemberSessionDao dao;
	
	public int login(String id, String pw, HttpServletRequest request) {

		dao = template.getMapper(MemberSessionDao.class);
		
		int loginChk = 0;

		MemberInfo memberInfo = null;

		memberInfo = dao.selectMemberById(id);

		if (memberInfo != null && memberInfo.pwChk(pw)) {
			//verify 값 체크
			if(memberInfo.getVerify()=='Y') {
				request.getSession(true).setAttribute("loginInfo", memberInfo.toLoginInfo());
				loginChk = 2;
			} else {
				request.getSession(true).setAttribute("reEmail", memberInfo.getuId());
				loginChk = 1;
			}
			
			// 세션에 저장(setAttribute)
			// loginChk 상태값을 변경
			
		
		}

		return loginChk;
	}
}
