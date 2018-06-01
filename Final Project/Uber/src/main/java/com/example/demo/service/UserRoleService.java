package com.example.demo.service;

import java.util.List;

import com.example.demo.model.UserRole;

public interface UserRoleService {
	
	List<UserRoleDTO> getAllUserRoles();
	
	UserRoleDTO getUserRoleById(Long id);
	
	UserRoleDTO getUserRoleByUser(UserDTO user);
	
	UserRoleDTO getUserByRole(String role);
	
	UserRoleDTO createUserRole(UserRoleDTO userRoleDTO);
	
	UserRoleDTO updateUserRole(Long id, UserRoleDTO userRoleDTO);
	
	void deleteUserRole(Long id);
}
