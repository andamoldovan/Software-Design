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
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping()
	public List<UserDTO> getUsers(){
		try {
			return userService.getAllUsers();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{id}")
	public UserDTO getUserById(@RequestParam("id") Long id) {
		try {
			return userService.getUserById(id);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{email}")
	public UserDTO getUserByEmail(@RequestParam("email") String email) {
		try {
			return userService.getUserByEmail(email);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{email, password}")
	public UserDTO getUserByEmailAndPassword(@RequestParam("email") String email, @RequestParam("password") String password) {
		try {
			return userService.getUserByEmailAndPassword(email, password);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping()
	public UserDTO createUser(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("address") String address, @RequestParam("phone") String phone, @RequestParam("iban") String iban) {
		UserDTO userDTO = new UserDTO(email, password, name, address, phone, iban);
		try {
			return userService.createUser(userDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PutMapping()
	public UserDTO updateUser(@RequestParam("id") Long id, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("address") String address, @RequestParam("phone") String phone, @RequestParam("iban") String iban) {
		UserDTO userDTO = new UserDTO(email, password, name, address, phone, iban);
		try {
			return userService.updateUser(id, userDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@DeleteMapping()
	public String deleteUser(@RequestParam("id") Long id) {
		try {
			userService.deleteUser(id);
			return "User at id: " + id + " was deleted";
		}catch(Exception e) {
			e.printStackTrace();
			return "User at id: " + id + " could not be deleted";
		}
	}
	
}
