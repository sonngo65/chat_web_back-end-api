package com.example.chatweb.entity;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Role {
	@Id
	@GeneratedValue(strategy= GenerationType.UUID)
	private UUID id;
	private String role;
	
	public Role(String role) {
	
		this.role = role;
	}

	@ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
	
	private Set<User> users;
	
}
