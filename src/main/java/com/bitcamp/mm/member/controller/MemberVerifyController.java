package com.bitcamp.mm.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitcamp.mm.member.service.MemberVerifyService;

@Controller
public class MemberVerifyController {

	@Autowired
	private MemberVerifyService verifyService;
	
	@RequestMapping("member/verify")
	@ResponseBody
	public String verify(@RequestParam("uId") String uId,
						 @RequestParam("code") String code) {
		
		String rStr = verifyService.verify(uId, code);
	
		return "member/verify"+rStr;
	}

	@RequestMapping("member/verify/reMailSend")
	@ResponseBody
	public String reMailSend(@RequestParam("uId") String uId) {
		
		int rCnt = verifyService.reMailSend(uId);
		
		return rCnt>0?"success":"fail";
	}
}
