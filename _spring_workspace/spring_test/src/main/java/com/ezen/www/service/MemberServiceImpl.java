package com.ezen.www.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ezen.www.domain.MemberVO;
import com.ezen.www.repository.MemberDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
	
	@Inject
	private MemberDAO mdao;
	
	@Inject
	BCryptPasswordEncoder passwordEncoder; //암호화하는 객체
	@Inject
	HttpServletRequest request;
	
	@Override
	public int signUp(MemberVO mvo) {
		// 아이디가 중복되면 회원가입 실패
		// => 아이디만 주고 DB에서 일치하는 mvo 객체를 리턴
		//일치하는 유저가 있다면 가입 실패, 없다면 가입 가능
		MemberVO tempMvo = mdao.getUser(mvo.getId());
		if(tempMvo != null) {
			//기존 아이디가 있는 경우
			return 0;
		}
		
		//아이디가 중복되지 않는다면 회원가입 진행
		//password가 null이거나 값이 없다면 가입불가.
		if(mvo.getId() == null || mvo.getId().length() == 0) {
			return 0;
		}
		if(mvo.getPw() == null || mvo.getPw().length() == 0) {
			return 0;
		}
		
		// 회원가입 진행
		// password는 암호화 하여 가입
		// 암호화(encode) /matches(입력된 비번, 암호화된비번) => ture / false
		String pw = mvo.getPw();
		
		String encodePw = passwordEncoder.encode(pw);
		mvo.setPw(encodePw);
		
		//회원가입
		int isOk = mdao.insert(mvo);
		
		return isOk;
	}

	@Override
	public MemberVO isUser(MemberVO mvo) {
		log.info("isMember serviceImpl");
		// 로그인 유지 확인
		// 아이디를 주고 해당 아이디의 객체를 리턴
		MemberVO tempMvo = mdao.getUser(mvo.getId()); //회원가입할 때 했던 메서드 호출
		
		//해당 아이디가 없는 경우
		if(tempMvo == null) {
			return null;
		}
		
		//matches(원래비번, 암호화된 비번) 비교
		if(passwordEncoder.matches(mvo.getPw(), tempMvo.getPw())) {
			return tempMvo;
		}
		
		return null;
	}

	@Override
	public void lastLogin(String id) {
		log.info("lastLogin service Impl");
		mdao.lastLogin(id);
	}

	@Override
	public int updateNePw(MemberVO mvo) {
		log.info("updateNePw service Impl");
		int isOk = mdao.updateNePw(mvo);
		return isOk;
	}

	@Override
	public int update(MemberVO mvo) {
		log.info("update service Impl");
		
		//새 비밀번호 암호화 후 저장
		String pw = mvo.getPw();
		String encodePw = passwordEncoder.encode(pw);
		mvo.setPw(encodePw);

		int isOk = mdao.update(mvo);
		return isOk;
	}

	@Override
	public int delete(String id) {
		return mdao.delete(id);
	}
	
	//선생님 풀이
//	@Override
//	public int modify(MemberVO mvo) {
		// pw의 여부에 따라서 변경사항을 나누어 처리
		// pw가 없다면 기존값으로 설정 / pw가 있다면 암호화 처리하여 수정
//		if(mvo.getPw() == null || mvo.getPw().length() == 0) {
//			MemberVO sesMvo = (MemberVO)request.getSession().getAttribute("ses");
//			mvo.setPw(sesMvo.getPw()); //자바에서 왠만한건 다 처리 -> DB처리 비용 > 서버 처리 비용
//		}else {
//			String setpw = passwordEncoder.encode(mvo.getPw());
//			mvo.setPw(setpw);
//		}
//		log.info(">>> pw 수정 후 mvo >> {} "+mvo);	
//		return mdao.update(mvo);
//	}
	
}
