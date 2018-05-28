package com.example.demo.service;

import java.io.Serializable;

import com.example.demo.model.Product;
import com.example.demo.model.User;

public class ChefProductDTO implements Serializable{
	
	private Long id;
	private UserDTO chef;
	private ProductDTO product;
	private int quantity;
	
	public ChefProductDTO() {
		
	}
	
	public ChefProductDTO(UserDTO chef, ProductDTO product, int quantity) {
		this.chef = chef;
		this.product = product;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getChef() {
		return chef;
	}

	public void setChef(UserDTO chef) {
		this.chef = chef;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}
	
	
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "User{" + 
					"id = " + id +
					", chef = " + chef.toString() +
					", product = " + product.toString() + 
					", quantity = " + quantity + '\'' +
					'}';
					
	}
}
