package com.myweb.www.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myweb.www.security.MemberVO;
import com.myweb.www.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member/*")
@Controller
public class MemberController {
	
	private final MemberService msv;
	private final BCryptPasswordEncoder bcEncoder;
	
	@GetMapping("/register")
	public void registerPage() {}
	
	@PostMapping("/register")
	public String register(MemberVO mvo) {
		mvo.setPwd(bcEncoder.encode(mvo.getPwd()));
		log.info(">>>>>>>>>>>>>> register @@@@@@@ mvo {}"+mvo);
		int isOk = msv.insert(mvo);
		log.info(">>>>>>>>> login insert is"+(isOk>0?"OK":"Fail"));
		return "index";
	}
	
	@GetMapping("/login")
	public void loginPage(){}
	
	//@postMappting("/login") 대신 CustomAuthMemberService 로 바로 타게됨
	
	//실패했을 때
	@PostMapping("/login")
	public String loginPost(HttpServletRequest request, RedirectAttributes re) {
		//로그인 실패 시 다시 로그인 페이지로 돌아와 오류 메시지 전송
		// 다시 로그인 유도
		re.addAttribute("email", request.getAttribute("email"));
		re.addAttribute("errMsg", request.getAttribute("errMsg"));
		return "redirect:/member/login";
	}
	
	//@RequestParam("email") String email
	@Transactional
	@GetMapping("/modify")
	public void detail(Principal p, Model m) {
		log.info("@@@@@@@@@@@@@@@@@@@ principal >> email >> "+p.getName());
		String email = p.getName();
		m.addAttribute("mvo", msv.detail(email));
	}
	
	@Transactional
	@PostMapping("/modify")
	public String modify(MemberVO mvo, HttpServletRequest request, HttpServletResponse respones) {
		if(mvo.getPwd().isEmpty()) {
			//비번 없는 업데이트 진행
			msv.noPwdUpdate(mvo);
		}else {
			//비번을 다시 인코딩 하여 업데이트 진행
			mvo.setPwd(bcEncoder.encode(mvo.getPwd()));
			msv.pwdUpdate(mvo);
		}
		//로그아웃 진행
		logout(request, respones);
		return "redirect:/member/login";
	}
	
	@Transactional
	@GetMapping("/list")
	public void list(Principal p, Model m) {
		m.addAttribute("mvoList", msv.mvoList());
	}
	
	@Transactional
	@GetMapping("/delete")
	public String delete(Principal p, HttpServletRequest request, HttpServletResponse respones) {
		msv.delete(p.getName());
		logout(request, respones);
		return "redirect:/";
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse respones) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		new SecurityContextLogoutHandler().logout(request, respones, authentication);
	}
	
}
