package com.bitcamp.mm.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bitcamp.mm.member.service.MemberDelService;

@Controller
public class MemberDelController {

	@Autowired
	private MemberDelService service;
	
	@RequestMapping(value="/member/confrimDel", method = RequestMethod.GET)
	public String confrimDelete() {
		return "/member/confrimDel";
	}
	
	@RequestMapping(value="/member/confrimDel", method = RequestMethod.POST)
	public String memberDelete(HttpSession session, 
							   @RequestParam(value="uPW") String uPW,
							   Model model) {
		
		int rChk = 0;
		
		rChk = service.memberDelete(session, uPW);
		
		model.addAttribute("rChk", rChk);
		
		return "/member/memberDel";
	}
	
	@RequestMapping("/member/memberDelete/{id}")
	public String delete(
			@PathVariable("id") int id
			) {
		
		service.memberDeleteManager(id);
		
		return "redirect:/member/memberList";
	}
}
