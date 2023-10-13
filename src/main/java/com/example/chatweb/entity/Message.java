package com.example.chatweb.entity;

import java.util.Date;
import java.util.UUID;

import org.springframework.core.annotation.Order;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String text;
	@OrderBy
	private Date time;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_send_id")
	private User userSend;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_received_id")
	private User userReceived;

	public Message(String text,  Date time) {
		super();
		this.text = text;
		this.time = time;
	}
}
