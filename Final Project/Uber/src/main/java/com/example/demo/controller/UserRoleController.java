package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.UserDTO;
import com.example.demo.service.UserRoleDTO;
import com.example.demo.service.UserRoleService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/user_roles")
public class UserRoleController {
	
	private final UserRoleService userRoleService;
	private final UserService userService;
	
	@Autowired
	public UserRoleController(UserRoleService userRoleService, UserService userService) {
		this.userRoleService = userRoleService;
		this.userService = userService;
	}
	
	
	@GetMapping
	public List<UserRoleDTO> getAllUserRoles(){
		try {
			return userRoleService.getAllUserRoles();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@GetMapping(value = "/{userRoleId}")
	public UserRoleDTO getUserById(@RequestParam("id") Long id) {
		try {
			return userRoleService.getUserRoleById(id);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{role}")
	public UserRoleDTO getUserByRole(@RequestParam("role") String role) {
		try{
			return userRoleService.getUserByRole(role);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping()
	public UserRoleDTO createUserRole(@RequestParam("user_id") Long userId, @RequestParam("role") String role) {
		UserDTO userDTO = userService.getUserById(userId);
		UserRoleDTO userRoleDTO = new UserRoleDTO(userDTO, role);
		try {
			return userRoleService.createUserRole(userRoleDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PutMapping
	public UserRoleDTO updateUserRole(@RequestParam("id") Long id, @RequestParam("user_id") Long userId, @RequestParam("role") String role) {
		UserDTO userDTO = userService.getUserById(userId);
		UserRoleDTO userRoleDTO = new UserRoleDTO(userDTO, role);
		try {
			return userRoleService.updateUserRole(id, userRoleDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@DeleteMapping
	public String deleteUserRole(@RequestParam("id") Long id) {
		try {
			userRoleService.deleteUserRole(id);
			return "User at id " + id + " was deleted";
		}catch(Exception e) {
			e.printStackTrace();
			return "User role at id " + id + " could not be deleted";
		}
	}
}
