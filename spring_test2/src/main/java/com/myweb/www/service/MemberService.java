package com.myweb.www.service;

import com.myweb.www.security.MemberVO;

public interface MemberService {

	int insert(MemberVO mvo);

	boolean updateLastLogin(String authEmail);

}
