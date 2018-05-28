package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserRoleDAO;
import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.example.demo.service.UserDTO;
import com.example.demo.service.UserRoleDTO;
import com.example.demo.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService{
	
	private final UserRoleDAO userRoleDAO;
	
	@Autowired
	public UserRoleServiceImpl(UserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}

	@Override
	public List<UserRoleDTO> getAllUserRoles() {
		return transform(userRoleDAO.findAll());
	}

	@Override
	public UserRoleDTO getUserRoleById(Long id) {
		return transform(userRoleDAO.getOne(id));
	}

	@Override
	public UserRoleDTO getUserByRole(String role) {
		try {
			return transform(userRoleDAO.findUserByRole(role));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public UserRoleDTO createUserRole(UserRoleDTO userRoleDTO) {
		return transform(userRoleDAO.save(transform(userRoleDTO)));
	}

	@Override
	public UserRoleDTO updateUserRole(Long id, UserRoleDTO userRoleDTO) {
		UserRole userRole = userRoleDAO.getOne(id);
		UserDTO userDTO = userRoleDTO.getUserDTO();
		userRole.setRole(userRoleDTO.getRole());
		userRole.setUser(new User(userDTO.getId(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getName(), userDTO.getAddress(), userDTO.getPhone(), userDTO.getIban()));
	
		return transform(userRoleDAO.save(userRole));
		
	}

	@Override
	public void deleteUserRole(Long id) {
		userRoleDAO.delete(userRoleDAO.getOne(id));
		
	}
	
	private UserRole transform(UserRoleDTO userRoleDTO) {
		UserRole userRole = new UserRole();
		UserDTO userDTO = userRoleDTO.getUserDTO();
		userRole.setRole(userRoleDTO.getRole());
		userRole.setUser(new User(userDTO.getId(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getName(), userDTO.getAddress(), userDTO.getPhone(), userDTO.getIban()));
	
		return userRole;
	}
	
	private UserRoleDTO transform(UserRole userRole) {
		UserRoleDTO userRoleDTO = new UserRoleDTO();
		User user = userRole.getUser();
		userRoleDTO.setId(userRole.getId());
		userRoleDTO.setRole(userRole.getRole());
		userRoleDTO.setUserDTO(new UserDTO(user.getEmail(), user.getPassword(), user.getName(), user.getAddress(), user.getPhone(), user.getIban()));
		userRoleDTO.getUserDTO().setId(user.getId());
		
		return userRoleDTO;
	}
	
	private List<UserRoleDTO> transform(List<UserRole> list){
		List<UserRoleDTO> result = new ArrayList<UserRoleDTO>();
		User user = new User();;
		UserRoleDTO userRoleDTO;
		
		for(UserRole u : list) {
			userRoleDTO = new UserRoleDTO();
			user = u.getUser();
			
			userRoleDTO.setId(u.getId());
			userRoleDTO.setRole(u.getRole());
			userRoleDTO.setUserDTO(new UserDTO(user.getEmail(), user.getPassword(), user.getName(), user.getAddress(), user.getPhone(), user.getIban()));
			userRoleDTO.getUserDTO().setId(user.getId());
			
			result.add(userRoleDTO);
		}
		return result;
	}

	
}
