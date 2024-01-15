package com.myweb.www.security;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {

	private String authEmail;
	private String errorMessage;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		setAuthEmail(request.getParameter("email"));
		
		//exception 발생 시 에러 메시지 저장
		if( exception instanceof BadCredentialsException || 
				exception instanceof InternalAuthenticationServiceException ) {
			setErrorMessage(exception.getMessage().toString());
		}
		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@2 errMsg >>>"+errorMessage);
		
		request.setAttribute("email", getAuthEmail());
		request.setAttribute("errMsg", getErrorMessage());
		request.getRequestDispatcher("/member/login?error").forward(request, response);
		
	}

}
