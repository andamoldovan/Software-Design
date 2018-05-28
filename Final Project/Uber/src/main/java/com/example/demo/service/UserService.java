package com.example.demo.service;

import java.util.List;

import com.example.demo.model.User;

public interface UserService {
	
	List<UserDTO> getAllUsers();
	
	UserDTO getUserById(Long id);
	
	UserDTO getUserByEmail(String email);
	
	UserDTO getUserByEmailAndPassword(String email, String password);
	
	UserDTO createUser(UserDTO userDTO);
	
	UserDTO updateUser(Long id, UserDTO userDTO);
	
	void deleteUser(Long id);
}
