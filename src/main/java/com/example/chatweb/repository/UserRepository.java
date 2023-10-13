package com.example.chatweb.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chatweb.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
	User findByNameAndPassword(String name,String password);
}
