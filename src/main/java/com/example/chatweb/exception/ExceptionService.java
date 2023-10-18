package com.example.chatweb.exception;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



public class ExceptionService {
	
	public static String convertPropertyToString(String[] fieldsName,Object[] fieldsValue) {
		String errorMessage = "";
		System.out.print(fieldsValue[0]);
		List<String> values =  Arrays.asList(fieldsValue).stream().map(filedValue -> (String)filedValue).collect(Collectors.toList());
		for(int i=0;i<fieldsName.length;i++) {
			errorMessage += String.format("%s : %s", fieldsName[i],values.get(i));
			if(i<fieldsName.length -1) {
				errorMessage += ", ";
			}
		}
		return errorMessage;
		
	}
}
