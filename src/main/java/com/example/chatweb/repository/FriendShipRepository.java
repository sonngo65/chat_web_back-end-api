package com.example.chatweb.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.chatweb.entity.FriendShip;
import com.example.chatweb.entity.User;

public interface FriendShipRepository extends JpaRepository<FriendShip, UUID>{
	
	@Query("SELECT n FROM FriendShip n WHERE n.user1 = ?1 OR n.user2 = ?1")
	List<FriendShip> findAllFriendShipByUser(User user);
}
