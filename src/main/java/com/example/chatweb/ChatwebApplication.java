package com.example.chatweb;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.convert.DurationFormat;


@SpringBootApplication
public class ChatwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatwebApplication.class, args);
		Date date = new Date(23);
		Date date1 = new Date(434);
		LocalDateTime start = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime end = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		System.out.print(Duration.between(start, end).toMillis() + "day");
	}

}
