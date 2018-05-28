package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;

@Repository
@Transactional
public interface UserDAO extends JpaRepository<User, Long>{
	
	User findUserByEmailAndPassword(String email, String password);
	
	User findUserByEmail(String email);
}
