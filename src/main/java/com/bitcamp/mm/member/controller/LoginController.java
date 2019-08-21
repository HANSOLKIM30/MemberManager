package com.bitcamp.mm.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bitcamp.mm.member.service.MemberLoginService;

@Controller
@RequestMapping("member/login")
public class LoginController {

	@Autowired
	private MemberLoginService service;
	
	@RequestMapping(method = RequestMethod.GET) //기본방식. 맨 처음에 login으로 들어오면 get으로 출력된다.
	public String loginForm(HttpServletRequest request) {
		
		String view = "member/loginForm";
		
		//세션을 가져오고, 세션이 없으면 생성하지 않는다.(null)
		HttpSession session = request.getSession(false);
		//세션이 null이 아니고, 세션에 loginInfo가 저장되어있다면(로그인 되어있다면), 메인페이지로 이동
		if(session != null && session.getAttribute("loginInfo")!=null) {
			view = "redirect:/main";
		}
		
		return view;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String login(@RequestParam("uId") String id,
						@RequestParam("uPW") String pw,
						HttpServletRequest request) {
		
		String view = "member/loginfail";
		
		//2019.08.21 login 후, 이메일 인증처리 여부 파악 
		int loginChk = 0;
		
		loginChk = service.login(id, pw, request);
		
		switch(loginChk) {
		case 1: 
			//새로운 view(jsp) 추가
			view = "member/notVerify";
			break;
		case 2:
			view = "redirect:/main";
			break;
		}
		
		return view;	
	}
}
