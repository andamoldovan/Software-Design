package com.example.demo.service;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class StudentDTO implements Serializable{
	
	private Long id;
	private String email;
	private String password;
	private String fullName;
	private int group;
	private String hobby;
	private String token;
	
	public StudentDTO() {
		
	}
	
	public StudentDTO(String email, int group, String hobby, String fullName, String password) {
		this.email = email;
		setPassword(password);
		this.fullName = fullName;
		this.group = group;
		this.hobby = hobby;

	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		String newPass = encryptPassword(password);
		this.password = newPass;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken() {
		UUID uuid = UUID.randomUUID();
		this.token = uuid.toString();
	}
	
	private String encryptPassword(String oldPassword) {
		String newPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(oldPassword.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			newPassword = sb.toString();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return newPassword;
	}

	
	@Override
	public String toString() {
		return "Student{" +
				"id = " + id +
				", email = " + email +
				", group = " + group +
				", hobby = " + hobby +
				", name = " + fullName +
				", password = " + password + '\'' +
				'}' + "\n";
	}
}
