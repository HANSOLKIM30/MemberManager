package com.bitcamp.mm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthCheckInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//세션에 loginInfo 속성이 존재하면 return true
		
		//현재 요청에서 세션 객체 받기
		HttpSession session = request.getSession(false);
		
		/*
		 * if(session != null) { //세션에서 loginInfo 속성 값을 받아온다. 
		 * 	Object auth = session.getAttribute("loginInfo");
		 * 	if(auth != null) { 
		 * 		return true; }}
		 */
		
		//→ 간단하게 변경
		if(session != null && session.getAttribute("loginInfo") != null) {
			return true;
		}
		
		response.sendRedirect(request.getContextPath() + "/member/login");
		
		return false;
	}

}
