package com.myweb.www.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myweb.www.repository.MemberDAO;
import com.myweb.www.security.AuthVO;
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

	@Override
	public MemberVO detail(String email) {
		// AuthVO도 같이 가져와야함
		MemberVO mvo = mdao.selectEmail(email);
		MemberVO mvo2 = new MemberVO(mvo.getEmail(), mvo.getPwd(), mvo.getNickName(), mvo.getRegAt(), mvo.getLastLogin() , mdao.selectAuths(email));
		return mvo2;
	}

	@Override
	public List<MemberVO> mvoList() {
		List<MemberVO> mvoList = mdao.selectAll();
		for(MemberVO mvo : mvoList) {
			mvo.setAuthList(mdao.selectAuths(mvo.getEmail()));
		}
		return mvoList;
	}

	@Override
	public void noPwdUpdate(MemberVO mvo) {
		 mdao.noPwdUpdate(mvo);
	}

	@Override
	public void pwdUpdate(MemberVO mvo) {
		// TODO Auto-generated method stub
		 mdao.pwdUpdate(mvo);
	}

	@Override
	public void delete(String name) {
		// TODO Auto-generated method stub
		mdao.authDelete(name);
		mdao.delete(name);
		
	}

	
}
