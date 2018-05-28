package com.example.demo.service;

import java.io.Serializable;

public class UserRoleDTO implements Serializable{

	private Long id;
	private UserDTO userDTO;
	private String role;
	
	public UserRoleDTO() {
		
	}
	
	public UserRoleDTO(UserDTO userDTO, String role) {
		this.userDTO = userDTO; 
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "UserRole{" + 
					"id = " + id +
					", user_id = " + userDTO.getId() +
					", role = " + role + '\'' +
					'}';
					
	}
}
