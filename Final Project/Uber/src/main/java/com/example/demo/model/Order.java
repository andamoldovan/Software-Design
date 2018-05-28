package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "customer_id", nullable = false)
	private User customer;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "chef_id", nullable = false)
	private User chef;
	
	@Column(name = "is_finalized")
	private String finalizeOrder;
	
	@Column(name = "rating")
	private int rating;
	
	@Column(name = "confirmed")
	private String confirmed;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "order_item", joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "id") },
										inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")})
	private Set<Product> orderProducts = new HashSet<Product>();
	
	public Order() {
		
	}
	
	public Order(Long id, User customer, User chef) {
		this.id = id;
		this.customer = customer;
		this.chef = chef;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}
	
	
	public User getChef() {
		return chef;
	}

	public void setChef(User chef) {
		this.chef = chef;
	}

	

	public Set<Product> getProducts() {
		return orderProducts;
	}

	public void setProducts(Set<Product> orderProducts) {
		this.orderProducts = orderProducts;
	}

	
	
	public String getFinalizeOrder() {
		return finalizeOrder;
	}

	public void setFinalizeOrder(String finalizeOrder) {
		this.finalizeOrder = finalizeOrder;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	
	
	public String getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}

	public Set<Product> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(Set<Product> orderProducts) {
		this.orderProducts = orderProducts;
	}

	@Override
	public String toString() {
		return "Order{" + 
					"id = " + id +
					", chef = " + chef.toString() +
					", customer = " + customer.toString() + '\'' +
					'}';
					
	}
}
