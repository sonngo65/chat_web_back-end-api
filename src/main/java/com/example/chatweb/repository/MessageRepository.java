package com.example.chatweb.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.chatweb.entity.Message;
import com.example.chatweb.entity.User;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID>{
	@Query("SELECT n FROM Message n WHERE (n.userSend=?1 And n.userReceived=?2 OR n.userSend=?2 And n.userReceived=?1) And (n.userSend!=n.userReceived Or n.userSend=n.userReceived And (n.userSend = ?1 And n.userSend = ?2) )")
	List<Message> listMessagesByUser(User user,User friend);
}
