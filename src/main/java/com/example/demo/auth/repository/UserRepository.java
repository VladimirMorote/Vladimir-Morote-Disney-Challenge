package com.example.demo.auth.repository;

import com.example.demo.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByUsername(String Username);
	
}
