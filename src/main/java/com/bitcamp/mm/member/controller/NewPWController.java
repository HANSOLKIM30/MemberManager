package com.bitcamp.mm.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitcamp.mm.member.service.NewPWService;

@Controller
public class NewPWController {

	@Autowired
	private NewPWService newPWService;
	
	@RequestMapping("member/changeNewPWForm")
	public String changePWForm() {
		return "member/changeNewPWForm";
	}
	
	@RequestMapping("member/changeNewPW")
	@ResponseBody
	public String changeNewPW(@RequestParam("uId") String uId) {
			
		String rStr = newPWService.changeNewPW(uId);
		
		return rStr;
	}
}
