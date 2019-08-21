package com.bitcamp.mm.member.service;

import java.util.Random;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitcamp.mm.member.dao.MemberSessionDao;

@Service("newPWService")
public class NewPWService {

	@Autowired
	private MailSenderService mailService;
	
	@Autowired
	private SqlSessionTemplate template;
	
	private MemberSessionDao dao;
	
	//새로운 패스워드로 update
	public String changeNewPW(String uId) {
		
		dao = template.getMapper(MemberSessionDao.class);
		
		//새로운 패스워드 만들기
		Random r = new Random(System.nanoTime());
		StringBuffer BuffertempPW = new StringBuffer();
		
		for(int i = 0; i < 5; i++) {
			if(r.nextBoolean()) {
				BuffertempPW.append(r.nextInt(10));
			}else {
				BuffertempPW.append((char)(r.nextInt(26)+97));
			}
		}
		
		System.out.println("Buffer 임시비밀번호 : "+BuffertempPW);
		
		String tempPW = BuffertempPW.toString();
		
		System.out.println("임시비밀번호 : " + tempPW);
		
		//데이터베이스 업데이트
		int rCnt = dao.updatePW(tempPW, uId);
		
		//메일 송부
		mailService.sendTempPw(tempPW, uId);
		
		return rCnt>0?"success":"fail";
	}
	
}
