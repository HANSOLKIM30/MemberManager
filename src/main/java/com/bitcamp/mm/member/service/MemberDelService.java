package com.bitcamp.mm.member.service;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitcamp.mm.member.dao.MemberSessionDao;
import com.bitcamp.mm.member.domain.LoginInfo;
import com.bitcamp.mm.member.domain.MemberInfo;

@Service
public class MemberDelService {

	// @Autowired private MemberDao dao;

	// @Autowired private MemberJdbcTemplateDao dao;

	// 자동 메퍼를 위한 sqlSessionTemplate 객체 주입
	// @Inject : 타입에 맞는 주입 ( java 에서 지원 : 특정 프레임워크에 의존하지 않음 )

	@Inject
	private SqlSessionTemplate template;

	private MemberSessionDao dao;

	public int memberDelete(HttpSession session, String uPW) {

		dao = template.getMapper(MemberSessionDao.class);

		int rChk = 0;

		LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
		System.out.println(loginInfo);

		MemberInfo memberInfo = dao.selectMemberById(loginInfo.getuId());

		if (memberInfo != null && memberInfo.pwChk(uPW)) {
			rChk = dao.deleteMember(memberInfo.getuId());
		}

		System.out.println(rChk);
		return rChk;
	}

	public int memberDeleteManager(String uId) {
		dao = template.getMapper(MemberSessionDao.class);
		int rCnt = dao.deleteMember(uId);
		System.out.println(rCnt);
		return rCnt;
	}
	
	public int memberDeleteManager(int idx) {

		// SqlSessionTemplate getMapper 를 이용해 dao 생성
		dao = template.getMapper(MemberSessionDao.class);

		return dao.memberDeleteManager(idx);
	}
}
