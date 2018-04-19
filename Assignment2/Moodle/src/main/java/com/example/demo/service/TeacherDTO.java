package com.example.demo.service;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TeacherDTO implements Serializable{

	private Long id;
	private String email;
	private String password;
	
	public TeacherDTO() {
		
	}
	
	public TeacherDTO(String email, String password) {
		this.email = email;
		setPassword(password);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		this.password = encryptPassword(password);
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
		return "Teacher{" +
				"id = " + id +
				", email = " + email +
				", password = " + password + '\'' +
				'}' + "\n";
	}
}
