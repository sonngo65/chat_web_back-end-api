package com.example.chatweb.exception;


import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.chatweb.payload.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleAllException(Exception exception,
            WebRequest webRequest){
		ErrorDetails errorDetails = new ErrorDetails(new Date(),exception.getMessage(),webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException e,WebRequest webRequest){
		ErrorDetails errorDetails = new ErrorDetails(new Date(),e.getMessage(),webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);		
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(),ex.getMessage(),request.getDescription(false));

		return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
