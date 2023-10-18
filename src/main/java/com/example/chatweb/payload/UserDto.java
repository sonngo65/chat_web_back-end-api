package com.example.chatweb.payload;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.chatweb.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private UUID userId;
	private String name;
	private String icon;
	private String color;
	private boolean isOnline;
	private String lastActive;
	
	public static UserDto convertToUserResponse(User user) {
		Date now = new Date();
		LocalDateTime start = user.getLastActive().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime end = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		Duration duration = Duration.between(start, end);
		String lastActive = "";
		if(duration.toDays()>0) {
			lastActive = duration.toDays() + "ngày";
		}else if(duration.toHours()>0) {
			lastActive = duration.toHours() + "giờ";
		}else if(duration.toMinutes()>0) {
			lastActive = duration.toMinutes() + "phút";
		}else {
			lastActive = duration.toSeconds() + "giây";
		}
		user.setIcon(ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(user.getIcon()).toUriString());
		return new UserDto(user.getUserId(),user.getName(),user.getIcon(),user.getColor(),user.isOnline(),lastActive);
	}
}
