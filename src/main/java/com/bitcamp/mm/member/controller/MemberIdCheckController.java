package com.bitcamp.mm.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitcamp.mm.member.service.MemberRegService;

@Controller

public class MemberIdCheckController {

	@Autowired
	private MemberRegService regService;
	
	//id를 받아서 결과값 Y / N을 Model에 담아 ajax로 전달한다.
	@RequestMapping("member/idCheck1")
	public String idCheck1(@RequestParam("id") String id, Model model) {
		
		model.addAttribute("result", regService.idCheck(id));
		
		return "member/idCheck";
	}
	
	
	 @RequestMapping("member/idCheck2") 
	 @ResponseBody 
	 public String idCheck2(@RequestParam("id") String id) {
	 
	  //model.addAttribute("result", regService.idCheck(id));
	  
	  return regService.idCheck(id);
	  
	  }
	 
	
}
