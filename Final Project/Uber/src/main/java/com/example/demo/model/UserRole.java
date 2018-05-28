package com.example.demo.model;

import javax.persistence.*;


@Entity
@Table(name = "user_roles", uniqueConstraints = {@UniqueConstraint(columnNames = "user_id")})
public class UserRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	
	@Column(name = "role")
	private String role;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	public UserRole() {
		
	}
	
	public UserRole(Long id, String role) {
		this.id = id;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public Long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}

	
	
	public String getRole() {
		return role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "UserRole{" + 
					"id = " + id +
					", user_id = " + user.getId() +
					", role = " + role + '\'' +
					'}';
					
	}
}
