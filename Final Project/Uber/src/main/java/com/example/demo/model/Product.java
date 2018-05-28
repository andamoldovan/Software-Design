package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "origin")
	private String origin;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "particularity_id", nullable = false)
	private Particularity particularity;
	
	@Column(name = "price")
	private float price;
	
	@ManyToMany(mappedBy = "products", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<User> chefs = new HashSet<>();
	
	@ManyToMany(mappedBy = "orderProducts", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Order> orders = new HashSet<>();
	
	

	public Product() {
		
	}
	
	public Product(Long id, String name, String description, String origin, Particularity particularity, float price) {
		this.id = id; 
		this.name = name;
		this.description = description;
		this.origin = origin;
		this.particularity = particularity;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Particularity getParticularity() {
		return particularity;
	}

	public void setParticularity(Particularity particularity) {
		this.particularity = particularity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public Set<User> getChefs() {
		return chefs;
	}

	public void setChefs(Set<User> chefs) {
		this.chefs = chefs;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	@Override
	public String toString() {
		return "User{" + 
					"id = " + id +
					", name = " + name +
					", description = " + description +
					", origin = " + origin +
					", particulatiry = " + particularity.toString() +
					", price = " + price + '\'' +
					'}';
					
	}
}
