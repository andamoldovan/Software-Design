package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.UserRole;

@Repository
@Transactional
public interface UserRoleDAO extends JpaRepository<UserRole, Long>{
	
	UserRole getUserRoleByUserId(Long id);
	
	UserRole findUserByRole(String role);
	
}
