package com.bitcamp.mm.member.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bitcamp.mm.member.domain.LoginInfo;
import com.bitcamp.mm.member.domain.MemberInfo;
import com.bitcamp.mm.member.domain.RequestMemberUpdate;
import com.bitcamp.mm.member.service.MemberUpdateService;

@Controller
public class MemberUpdateController {

	@Autowired
	private MemberUpdateService service;
	
	@RequestMapping(value="member/updateForm", method = RequestMethod.GET)
	public String getUpdateMember(Model model, HttpServletRequest request) {
		
			LoginInfo loginInfo = (LoginInfo) request.getSession(false).getAttribute("loginInfo");
		
			MemberInfo memberInfo = service.getUpdateForm(loginInfo.getuId());
		
			model.addAttribute("updateData", memberInfo);
			
			return "member/updateForm";
	}
	
	@RequestMapping(value="member/updateForm", method = RequestMethod.POST)
	public String postUpdateMember(HttpServletRequest request, 
								   @RequestParam("oldFile") String oldFileName, 
								   RequestMemberUpdate update,
								   Model model
								   ) {
		
		int resultCnt = 0;

		resultCnt = service.updateMember(request, oldFileName, update);
		
		System.out.println(resultCnt);
		
		model.addAttribute("rCnt", resultCnt);
		
		return "member/memberUpdate";
	}
	
	@RequestMapping("member/updateFormManager")
	public String editForm(
			@RequestParam("memberId") int id,
			Model model
			) {
		
		MemberInfo memberInfo = service.getEditFormData(id);
		
		model.addAttribute("updateData", memberInfo);
		
		return "member/updateForm";
		
	}
	
	
	
}
