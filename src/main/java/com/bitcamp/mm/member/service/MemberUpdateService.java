package com.bitcamp.mm.member.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitcamp.mm.member.dao.MemberSessionDao;
import com.bitcamp.mm.member.domain.MemberInfo;
import com.bitcamp.mm.member.domain.RequestMemberUpdate;

@Service("updateService")
public class MemberUpdateService {

	//@Autowired private MemberDao dao;
	
	//@Autowired private MemberJdbcTemplateDao dao;
	
	@Autowired
	private SqlSessionTemplate template;
	
	private MemberSessionDao dao;
	
	public MemberInfo getUpdateForm(String uId) {
		
		dao = template.getMapper(MemberSessionDao.class);
		
		MemberInfo memberInfo = null;
		
		memberInfo = dao.selectMemberById(uId);
						
		return memberInfo;
	}
	
	public MemberInfo getUpdateFormByIdx(int idx) {
		
		dao = template.getMapper(MemberSessionDao.class);
		
		MemberInfo memberInfo = null;
		
		memberInfo = dao.selectMemberByIdx(idx);
						
		return memberInfo;
	}
	
	public int updateMember(HttpServletRequest request, String oldFileName, RequestMemberUpdate update) {
		
		dao = template.getMapper(MemberSessionDao.class);
		
		String path = "/uploadFile/userphoto";
		String dir = request.getSession().getServletContext().getRealPath(path);
		
		MemberInfo memberInfo = update.toMemberInfo();
		
		int resultCnt = 0;
		
		//새로운 파일이름 생성
		String newFileName = "";
		
		try {
			
			if(update.getNewFile() != null && !update.getNewFile().isEmpty() && update.getNewFile().getSize() > 0) {
				
				newFileName = "new" + memberInfo.getuId() + System.nanoTime();
				
				update.getNewFile().transferTo(new File(dir, newFileName));
				
				System.out.println("새로운 파일 이름 : " + newFileName);
				
				//새로운 파일 저장
				memberInfo.setuPhoto(newFileName);
				// 이전 파일 삭제
				new File(dir, oldFileName).delete();
				
			} else {
				memberInfo.setuPhoto(oldFileName);
			}

			resultCnt = dao.updateMember(memberInfo);
			
			if(memberInfo != null && request.getSession().getAttribute("loginInfo") != null) {
				request.getSession(false).invalidate();
				request.getSession(true).setAttribute("loginInfo", memberInfo.toLoginInfo());
			}
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("오류");
			//new File(dir,newFileName).delete();
		}
		
		return resultCnt;
	}

	public MemberInfo getEditFormData(int id) {
				// SqlSessionTemplate getMapper 를 이용해 dao 생성
				dao = template.getMapper(MemberSessionDao.class);
				
				MemberInfo memberInfo = dao.selectMemberByIdx(id);
				return memberInfo;
	}
}
