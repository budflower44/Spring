package com.ezen.www.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.www.domain.MemberVO;
import com.ezen.www.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/member/*")
@Controller
public class MemberController {
	
	@Inject
	private MemberService msv;
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(MemberVO mvo) {
		log.info(">>> mvo >> {} "+mvo);
		int isOk = msv.signUp(mvo);
		log.info(">>>> signUp ? "+ (isOk>0?"OK":"Fail"));
		return "index";
	}
	
	@GetMapping("/login")
	public void login() {}
	
	@PostMapping("/login")
	public String login(MemberVO mvo, Model m, HttpServletRequest request) {
		log.info(">>> mvo >> {} "+mvo);
		//mvo 객체가 db에 값과 일치하는지 체크
		MemberVO loginMvo = msv.isUser(mvo);
		
		if(loginMvo != null) {
			//로그인 성공 시
			HttpSession ses = request.getSession();
			ses.setAttribute("ses", loginMvo); //session에 로그인 객체 저장
			ses.setMaxInactiveInterval(60*10); //로그인 유지 시간
			m.addAttribute("msg_login_sucess", "1");
		}else {
			//로그인 실패 시
			m.addAttribute("msg_login", "-1");
		}
		
		return "index";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, Model m) {
		//세션 객체 삭제 => 세션 끊기
		MemberVO mvo = (MemberVO)request.getSession().getAttribute("ses");
		msv.lastLogin(mvo.getId());
		request.getSession().removeAttribute("ses");
		request.getSession().invalidate();
		m.addAttribute("msg_logout", "1");
		return "index";
	}
	
	@GetMapping("/modify")
	public void modify() {}
	
	@PostMapping("/modify")
	public String modify(MemberVO mvo, HttpServletRequest request, Model m) {
		log.info(">>> mvo >> {} "+mvo);
		if(mvo.getPw()=="") { //password 수정하지 않는 케이스
			int isOk = msv.updateNePw(mvo);
			log.info(">>> modifyupdate Ne Pw "+(isOk>0?"OK":"Fail"));
			MemberVO sesMvo = (MemberVO)request.getSession().getAttribute("ses");
			msv.lastLogin(mvo.getId());
			request.getSession().removeAttribute("ses");
			request.getSession().invalidate();
			m.addAttribute("msg_modify", "1");
		}else { //password 수정하는 케이스
			int isUp = msv.update(mvo);
			log.info(">>> modify update All "+(isUp>0?"OK":"Fail"));
			MemberVO sesMvo = (MemberVO)request.getSession().getAttribute("ses");
			msv.lastLogin(mvo.getId());
			request.getSession().removeAttribute("ses");
			request.getSession().invalidate();
			m.addAttribute("msg_modify", "1");
		}
		return "index";
		
	}
	
	//선생님 풀이
//	@GetMapping("/modify")
//	public void modify(HttpServletRequest request) {}
	
//	@PostMapping("/modify")
//	public String modify(MemberVO mvo, RedirectAttributes re) {
//		log.info(">>> mvo >> {} "+mvo);
//		int isOk = msv.modify(mvo);
//		log.info(">>> modify ? >> "+(isOk>0?"OK":"Fail"));
//		re.addFlashAttribute("msg_modify", isOk);
//		return "redirect:/member/logout";
//	}
	
	@GetMapping("/delete")
	public String delete(HttpServletRequest request, Model m) {
		String id = ((MemberVO)request.getSession().getAttribute("ses")).getId();
		log.info(id);
		int isOk = msv.delete(id);
		log.info("delete is >>> "+(isOk>0?"OK":"Fail"));
		m.addAttribute("msg_delete", "1");
		request.getSession().removeAttribute("ses");
		request.getSession().invalidate();
		return "index";
	}
	
	
	
	
}
