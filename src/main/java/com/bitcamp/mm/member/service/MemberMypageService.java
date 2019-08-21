package com.bitcamp.mm.member.service;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitcamp.mm.member.dao.MemberSessionDao;
import com.bitcamp.mm.member.domain.LoginInfo;
import com.bitcamp.mm.member.domain.MemberInfo;

@Service("myPageService")
public class MemberMypageService {

	//@Autowired private MemberDao dao;
	
	//@Autowired private MemberJdbcTemplateDao dao;
	
	@Autowired
	private SqlSessionTemplate template;
	
	private MemberSessionDao dao;
	
	public MemberInfo getMember(HttpServletRequest request) {
		
		dao = template.getMapper(MemberSessionDao.class);
		
		MemberInfo memberInfo = null;
		
		LoginInfo loginInfo = (LoginInfo)request.getSession(false).getAttribute("loginInfo");
		
		memberInfo = dao.selectMemberById(loginInfo.getuId());
		
		return memberInfo;
	}
}
