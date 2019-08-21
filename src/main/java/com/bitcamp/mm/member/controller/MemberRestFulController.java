package com.bitcamp.mm.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitcamp.mm.member.domain.MemberInfo;
import com.bitcamp.mm.member.domain.RequestMemberRegist;
import com.bitcamp.mm.member.domain.RequestMemberUpdate;
import com.bitcamp.mm.member.service.MemberDelService;
import com.bitcamp.mm.member.service.MemberListService;
import com.bitcamp.mm.member.service.MemberRegService;
import com.bitcamp.mm.member.service.MemberUpdateService;

@RestController //@ResponseBody 생략
@RequestMapping("/rest/members")
public class MemberRestFulController {
	
	@Autowired
	private MemberListService listService;
	
	@Autowired
	private MemberDelService deleteService;
	
	@Autowired
	private MemberRegService regService;
	
	@Autowired
	private MemberUpdateService updateService;
	
	//@RequestMapping(method=RequestMethod.GET)
	//@ResponseBody
	//@CrossOrigin
	@GetMapping
	public ResponseEntity<List<MemberInfo>> getAllList(){
		
		List<MemberInfo> list = listService.getAllList();
		
		ResponseEntity<List<MemberInfo>> entity = new ResponseEntity<List<MemberInfo>>(list, HttpStatus.OK);
		
		//HttpStatus.OK → 200
		//HttpStatus.NOT_FOUND → 404
		//HttpStatus.INTERNAL_SERVER_ERROR → 500
		
		return entity;
	}
	
	@CrossOrigin
	@DeleteMapping("/{uId}") // /rest/members/12
	public ResponseEntity<String> deleteMember(@PathVariable("uId") String uId) {
		
		int cnt = deleteService.memberDeleteManager(uId);
		System.out.println(cnt);
		
		return new ResponseEntity<String>(cnt>0?"success":"fail",HttpStatus.OK);
	}
	
	
	//@RequestMapping(method = RequestMethod.POST) 대체 PostMapping
	//커맨드객체 사용
	@CrossOrigin
	@PostMapping
	public ResponseEntity<String> regMember(RequestMemberRegist regRequest, HttpServletRequest request) {
		
		System.out.println("check : " + regRequest);
		
		int cnt = regService.memberInsert(request, regRequest);
		
		return new ResponseEntity<String>(cnt>0?"success":"fail",HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/{idx}")
	public ResponseEntity<MemberInfo> updateForm(@PathVariable("idx") int idx){
		
		MemberInfo memberInfo = null;
		memberInfo = updateService.getUpdateFormByIdx(idx);
		
		return new ResponseEntity<MemberInfo>(memberInfo, HttpStatus.OK);
	}
	
	@CrossOrigin
	@PutMapping("/{idx}")
	public ResponseEntity<String> updateMember(
			@PathVariable("idx") int idx, 
			HttpServletRequest request, 
			@RequestBody RequestMemberUpdate update) {

		update.setIdx(idx);
		System.out.println(update);
		
		
		int cnt = updateService.updateMember(request, null, update);
		
		System.out.println(cnt);
		
		return new ResponseEntity<String>(cnt>0?"success":"fail",HttpStatus.OK);
	}
}
