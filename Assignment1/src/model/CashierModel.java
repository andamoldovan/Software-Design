package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CashierModel {
	private int id;
	private String username;
	private String password;
	private int admin;
	
	public CashierModel() {
		
	}
	
	public CashierModel(int id, String username, String password, int admin) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.admin = admin;
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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		//String encryptedPassword = encryptPassword(password);
		//return encryptedPassword;
		return password;
	}

	public String getEncriptedPassword() {
		String encryptedPassword = encryptPassword(password);
		return encryptedPassword;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}
	
	public void sellTicket(ShowModel show) {
		show.setNrTickets(show.getNrTickets() - 1);
	}
}
