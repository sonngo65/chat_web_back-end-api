package com.example.chatweb.security;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.example.chatweb.payload.ErrorDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

//		ErrorDetails errorDetails = new ErrorDetails(new Date(),"Unauthorized",authException.getMessage());
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String,String> errorDetails = new HashMap<String,String>();
		errorDetails.put("timestamp", new Date().toString());
		errorDetails.put("message", "Unauthorized");
		errorDetails.put("details", authException.getMessage());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String responseError =  objectMapper.writeValueAsString(errorDetails);
        response.getOutputStream().println(responseError);

	}

}
