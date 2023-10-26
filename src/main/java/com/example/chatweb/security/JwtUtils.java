package com.example.chatweb.security;

import java.security.Key;
import java.util.Date;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	@Value("${application.jwt.secret}")
	private String jwtSecret;
	
	private static long expiration = 100*60*60*24;
	
	public String generateJwtToken(String userName){
		
		return Jwts.builder()
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(signKey(jwtSecret))
				.compact();
				
	}
	
	public Claims extractClaimsFromJwtToKen(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(signKey(jwtSecret))
				.build()
				.parseClaimsJws(token)
				.getBody();
				
	}
	public String extractUsernameFromToken(String token) {
		return extractClaimsFromJwtToKen(token).getSubject();
	}
	
	public boolean isExpired(String token) {
		return new Date().before(extractExpirationFromToken(token));
	}
	
	public Date extractExpirationFromToken(String token) {
		return extractClaimsFromJwtToKen(token).getExpiration();
	}
	
	private Key signKey(String jwtSecret) {
		byte[] key = Decoders.BASE64.decode(jwtSecret);
		return Keys.hmacShaKeyFor(key);
		
	}
}
