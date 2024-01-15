package com.ezen.www.service;

import javax.servlet.http.HttpServletRequest;

import com.ezen.www.domain.MemberVO;

public interface MemberService {

	int signUp(MemberVO mvo);

	MemberVO isUser(MemberVO mvo);

	void lastLogin(String id);

	int updateNePw(MemberVO mvo);

	int update(MemberVO mvo);

	int delete(String id);
	
	//선생님 풀이
//	int modify(MemberVO mvo);
	
}
