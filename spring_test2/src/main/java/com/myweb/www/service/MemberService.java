package com.myweb.www.service;

import java.util.List;

import com.myweb.www.security.AuthVO;
import com.myweb.www.security.MemberVO;

public interface MemberService {

	int insert(MemberVO mvo);

	boolean updateLastLogin(String authEmail);

	MemberVO detail(String email);

	List<MemberVO> mvoList();

	void noPwdUpdate(MemberVO mvo);

	void pwdUpdate(MemberVO mvo);

	void delete(String name);


}
