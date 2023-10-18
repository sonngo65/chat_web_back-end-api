package com.example.chatweb.exception;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Data
public class ResourceNotFoundException extends RuntimeException{
	private String resourceName;
	private String[] fieldsName;
	private Object[] fieldsValue;
	public ResourceNotFoundException(String resourceName, String[] fieldsName, Object[] fieldsValue) {
		
		super(String.format("%s not found with %s",resourceName, ExceptionService.convertPropertyToString(fieldsName, fieldsValue)));
		this.resourceName = resourceName;
		this.fieldsName = fieldsName;
		this.fieldsValue = fieldsValue;
	}
	
	
	
}
