package com.bitcamp.mm.member.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bitcamp.mm.member.domain.RequestMemberRegist;
import com.bitcamp.mm.member.service.MailSenderService;
import com.bitcamp.mm.member.service.MemberRegService;

@Controller
@RequestMapping("/member/regist")
public class MemberRegController {

	@Autowired
	private MemberRegService service;
	
	/*
	 * @Autowired private MailSenderService mailService;
	 */
	
	@RequestMapping(method = RequestMethod.GET)
	public String getForm() {
		return "/member/registForm";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	//postForm 메서드에 parameter로 담음으로서, regist 객체를 커맨드 객체로 활용할 수 있게 된다.
	//form에서 넘어오는 값들이 regist 커맨드 객체에 자동으로 저장됨.
	//단, form에서 넘어오는 요소들의 이름, 개수가 같아야하며 다를 경우, default 및 해당 변수 개수만큼의 생성자를 추가해 주어야 한다.
	public String postForm(RequestMemberRegist regist, HttpServletRequest request, Model model) {
		
		System.out.println(regist);
		
		int resultCnt = 0;
		resultCnt = service.memberInsert(request, regist);
		
		model.addAttribute("rCnt", resultCnt);
		
		//mailService.send(regist.getuId());
		
		return "/member/memberRegist";
	}
}
