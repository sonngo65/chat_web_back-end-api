package com.example.chatweb.exception;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.example.chatweb.payload.ErrorDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		ObjectMapper objectMapper = new ObjectMapper();
		ErrorDetails errorDetails = new ErrorDetails(new Date(),"Unauthorized",accessDeniedException.getMessage());
		response.setStatus(401);
		response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
		
	}

}
