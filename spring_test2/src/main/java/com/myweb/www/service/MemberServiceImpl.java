package com.myweb.www.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myweb.www.repository.MemberDAO;
import com.myweb.www.security.MemberVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{
	
	private final MemberDAO mdao;
	
//	BCryptPasswordEncoder passwordEncoder; //암호화하는 객체
	
	@Transactional
	@Override
	public int insert(MemberVO mvo) {
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ seviceImpl mvo"+mvo);
		int isOk = mdao.insert(mvo);
		log.info(">>>>>>>>>>>>>>>>>>>>> insert DAO is"+(isOk>0?"OK":"Fail"));
		return mdao.insertAuthInit(mvo.getEmail());
	}

	@Override
	public boolean updateLastLogin(String authEmail) {
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ seviceImpl mvo"+authEmail);
		return mdao.updateLastLogin(authEmail) >0 ? true : false;
	}
	
	
	
}