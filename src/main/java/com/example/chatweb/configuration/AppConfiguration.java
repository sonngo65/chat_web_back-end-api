package com.example.chatweb.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.chatweb.entity.Message;

@Configuration
public class AppConfiguration {
	@Bean
	public Comparator<Message> comparator(){
		return new Comparator<Message>() {

			@Override
			public int compare(Message o1, Message o2) {
				// TODO Auto-generated method stub
				return o1.getTime().before(o2.getTime()) ? -1 : 1;
			}
			
		};
		
	}
	
	@Bean 
	public Path createPath() {
		return Paths.get("src/main/java/com/example/chatweb/image");
	}

}	
