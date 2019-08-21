package com.bitcamp.mm.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bitcamp.mm.member.domain.LoginInfo;

@Controller
public class MemberLogoutController {

	@RequestMapping("member/logout")
	public String logout(HttpSession session) {
		
		LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
		System.out.println(loginInfo);
		
		session.invalidate();
		
		return "redirect:/main";
	}
}
