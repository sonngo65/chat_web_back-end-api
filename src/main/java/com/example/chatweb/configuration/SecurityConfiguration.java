package com.example.chatweb.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.chatweb.exception.AccessDeniedHandlerImpl;
import com.example.chatweb.security.JwtAuthenticationEntryPoint;
import com.example.chatweb.security.JwtAuthenticationFilter;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@EnableWebSecurity
@SecurityScheme(name = "Authorization Bearer",
			type = SecuritySchemeType.HTTP,
			bearerFormat = "JWTs",
			scheme = "bearer"
		)
public class SecurityConfiguration  {
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AccessDeniedHandlerImpl accessDeniedHandlerImpl;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Bean  
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.cors(Customizer.withDefaults())
				.authorizeHttpRequests(authorizeHttpRequest -> authorizeHttpRequest
						.requestMatchers("/api/v1/security/login","/swagger-ui/**","/v3/api-docs/**").permitAll()
						.anyRequest().authenticated()
						)
				.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(exception-> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthenticationFilter,  UsernamePasswordAuthenticationFilter.class)			
				.build();
		
	}
	
	@Bean 
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
}
