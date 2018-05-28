package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "email")
	private String email;
	
	
	@Column(name = "fullname")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone_no")
	private String phone;
	
	@Column(name = "iban")
	private String iban;
	
	@Column(name = "password")
	private String password;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
	private Set<UserRole> userRole = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "chef_product", joinColumns = {@JoinColumn(name = "chef_id", referencedColumnName = "id")}, 
									inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")})
	private Set<Product> products = new HashSet<>();
	
	public User() {
		
	}
	
	public User(Long id, String email, String password, String name, String address, String phone, String iban) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.iban = iban;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "User{" + 
					"id = " + id +
					", email = " + email +
					", address = " + address +
					", name = " + name + '\'' +
					'}';
					
	}
}
