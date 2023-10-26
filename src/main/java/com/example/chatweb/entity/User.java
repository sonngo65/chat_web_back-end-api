package com.example.chatweb.entity;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID userId;
	private String name;
	private String password;
	private String icon;
	private String color;

	private boolean isOnline;
	private Date lastActive;
	public User(String name, String password, String icon,String color, boolean isOnline, Date lastActive) {
		super();
		this.name = name;
		this.password = password;
		this.icon = icon;
		this.color = color;
		this.isOnline = isOnline;
		this.lastActive = lastActive;
	}

	public User(String name, String password, Set<Role> roles) {
		this.name = name;
		this.password = password;
		this.roles = roles;
	}

	@OneToMany(mappedBy="userSend",cascade = CascadeType.ALL)
	private Set<Message> sendMessages;
	

	@OneToMany(mappedBy= "userReceived",cascade=CascadeType.ALL)
	private Set<Message>  receivedMessage;
	
	@OneToMany(mappedBy = "user1",cascade = CascadeType.ALL)
	private Set<FriendShip> friendShip1;
	
	@OneToMany(mappedBy = "user2",cascade = CascadeType.ALL)
	private Set<FriendShip> friendShip2;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
			
			)	
	private Set<Role> roles;
}

