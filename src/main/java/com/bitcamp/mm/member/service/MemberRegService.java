package com.bitcamp.mm.member.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitcamp.mm.member.dao.MemberSessionDao;
import com.bitcamp.mm.member.domain.MemberInfo;
import com.bitcamp.mm.member.domain.RequestMemberRegist;

@Service("registService")
public class MemberRegService implements MemberService {

	//@Autowired private MemberDao dao;
	
	//@Autowired MemberJdbcTemplateDao dao;
	
	@Autowired
	private MailSenderService mailService;
	
	@Autowired
	private SqlSessionTemplate template;
	
	private MemberSessionDao dao;
	
	public int memberInsert(HttpServletRequest request, RequestMemberRegist regist) {
		
		dao = template.getMapper(MemberSessionDao.class);
		
		String path = "/uploadFile/userphoto"; //리소스 매핑 필요(servlet-context.xml에서 지정)
		String dir = request.getSession().getServletContext().getRealPath(path);
		
		MemberInfo memberInfo = regist.toMemberInfo();

		int resultCnt = 0;
		
		String newFileName = "";
		
		try {
			
			if(regist.getuPhoto() != null) {
				//새로운 파일이름 생성
				newFileName = memberInfo.getuId() + System.nanoTime() + "";
				// newFileName = memberInfo.getuId() + "_" + regist.getuPhoto().getOriginalFilename();
				
				//파일을 서버의 지정 경로에 저장
				regist.getuPhoto().transferTo(new File(dir, newFileName));
				
				//데이터베이스 저장을 하기 위한 파일이름 set
				memberInfo.setuPhoto(newFileName);
				}
			
			resultCnt = dao.insertMember(memberInfo);
			
			//메일발송
			mailService.send(memberInfo.getuId(), memberInfo.getCode());
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			System.out.println("오류 발생!");
			if(regist.getuPhoto()!=null) {
				new File(dir,newFileName).delete();
			}
		} 

		return resultCnt;
	}	
	
	/*
	 * public char idCheck(String id) {
	 * 
	 * dao = template.getMapper(MemberSessionDao.class);
	 * 
	 * char chk = dao.selectMemberById(id)==null?'Y':'N';
	 * 
	 * return chk; }
	 */
	
	
	  public String idCheck(String id) {
	
		  dao = template.getMapper(MemberSessionDao.class);
		  
		  String chk = dao.selectMemberById(id)==null?"Y":"N";
	  
		  return chk; 
	  }
	 
}
