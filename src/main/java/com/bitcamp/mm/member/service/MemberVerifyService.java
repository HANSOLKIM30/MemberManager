package com.bitcamp.mm.member.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitcamp.mm.member.dao.MemberSessionDao;
import com.bitcamp.mm.member.domain.MemberInfo;

@Service("verifyService")
public class MemberVerifyService {
	
	@Autowired
	private MailSenderService mailService;
	
	@Autowired
	private SqlSessionTemplate template;
	
	private MemberSessionDao dao;

	public String verify(String uId, String code) {
		
		dao = template.getMapper(MemberSessionDao.class);
		
		int rCnt = dao.updateVerify(uId, code);
		
		return rCnt>0?"success":"fail";
	}
	
	public int reMailSend(String uId) {
		
		dao = template.getMapper(MemberSessionDao.class);
		
		MemberInfo member = dao.selectMemberById(uId);
		
		mailService.reSend(member.getuId(), member.getCode());
	
		return 1;
	}
}
