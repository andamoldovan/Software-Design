package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.PasswordEncoder;
import com.example.demo.dao.UserDAO;
import com.example.demo.model.User;
import com.example.demo.service.UserDTO;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private final UserDAO userDAO;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
		this.userDAO = userDAO;
		this.passwordEncoder = passwordEncoder;
	}
	
	public List<UserDTO> getAllUsers(){
		return transform(userDAO.findAll());
	}
	

	@Override
	public UserDTO getUserById(Long id) {
		try {
			return transform(userDAO.getOne(id));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public UserDTO getUserByEmailAndPassword(String email, String password) {
		try {
			return transform(userDAO.findUserByEmailAndPassword(email, password));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public UserDTO getUserByEmail(String email) {
		try {
			return transform(userDAO.findUserByEmail(email));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public UserDTO createUser(UserDTO userDTO) {
		userDTO.setPassword(passwordEncoder.passwordEncoder().encode(userDTO.getPassword()));
		return transform(userDAO.save(transform(userDTO)));
	}
	

	@Override
	public UserDTO updateUser(Long id, UserDTO userDTO) {
		User user = userDAO.getOne(id);
		user.setEmail(userDTO.getEmail());
		user.setPassword(passwordEncoder.passwordEncoder().encode(userDTO.getPassword()));
		//user.setPassword(userDTO.getPassword());
		user.setName(userDTO.getName());
		user.setAddress(userDTO.getAddress());
		user.setPhone(userDTO.getPhone());
		user.setIban(userDTO.getIban());
		return transform(userDAO.save(user));
	}

	@Override
	public void deleteUser(Long id) {
		userDAO.delete(userDAO.getOne(id));
		
	}
	
	private List<UserDTO> transform(List<User> list){
		List<UserDTO> result = new ArrayList<UserDTO>();
		
		UserDTO userDTO;
		for(User u : list) {
			userDTO = new UserDTO();
			userDTO.setId(u.getId());
			userDTO.setAddress(u.getAddress());
			userDTO.setEmail(u.getEmail());
			userDTO.setPassword(u.getPassword());
			userDTO.setName(u.getName());
			userDTO.setPhone(u.getPhone());
			userDTO.setIban(u.getIban());
			result.add(userDTO);
		}
		
		return result;
	}
	
	
	
	private User transform(UserDTO userDTO) {
		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setName(userDTO.getName());
		user.setAddress(userDTO.getAddress());
		user.setPhone(userDTO.getPhone());
		user.setIban(userDTO.getIban());
		return user;
	}
	
	private UserDTO transform(User user) {
		UserDTO userDTO = new UserDTO();
		if(user == null) {
			return null;
		}
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setPassword(user.getPassword());
		userDTO.setName(user.getName());
		userDTO.setAddress(user.getAddress());
		userDTO.setPhone(user.getPhone());
		userDTO.setIban(user.getIban());
		return userDTO;
	}

	

	

}
