package com.example.demo.service;

import java.io.Serializable;

public class OrderDTO implements Serializable{
	
	private Long id;
	private UserDTO customer;
	private UserDTO chef;
	private String finalizeOrder;
	private int rating;
	private String confirmed;
	
	public OrderDTO() {
		
	}
	
	public OrderDTO(UserDTO customer, UserDTO chef) {
		this.customer = customer;
		this.chef = chef;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getCustomer() {
		return customer;
	}

	public void setCustomer(UserDTO customer) {
		this.customer = customer;
	}
	
	public UserDTO getChef() {
		return chef;
	}

	public void setChef(UserDTO chef) {
		this.chef = chef;
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

	@Override
	public String toString() {
		return  "Order{" + 
					"id = " + id +
					", chef = " + chef.toString() +
					", customer = " + customer.toString() + 
					", finalized = " + finalizeOrder + 
					", rating = " + rating + 
					", confirmed = " + confirmed + '\'' +
					'}';
					
	}
}
