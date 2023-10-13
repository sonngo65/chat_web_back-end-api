package com.example.chatweb.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurationSupport;

@Configuration

public class WebsocketConfiguration extends   WebSocketMessageBrokerConfigurationSupport{

	private static final String WEB_SOCKET_CONNECT_URL = "/ws/connect" ;
	public static final String SIMPLE_CAT_BOX_URL = "/ws/simple-cat-box";
	@Override
	protected void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(WEB_SOCKET_CONNECT_URL)
				.setAllowedOrigins("http://localhost:3000")
				.withSockJS();
		
	}
	@Override
	protected void configureMessageBroker(MessageBrokerRegistry registry) {
		super.configureMessageBroker(registry);
		registry.enableSimpleBroker(SIMPLE_CAT_BOX_URL);
		
	}
	

}
