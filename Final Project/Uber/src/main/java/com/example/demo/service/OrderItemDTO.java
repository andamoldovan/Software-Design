package com.example.demo.service;

import java.io.Serializable;

public class OrderItemDTO implements Serializable{
	
	private Long id;
	private OrderDTO order;
	private ProductDTO product;
	private int quantity;
	private float finalPrice;
	
	public OrderItemDTO() {
		
	}
	
	public OrderItemDTO(OrderDTO order, ProductDTO product, int quantity) {
		this.order = order;
		this.product = product;
		this.quantity = quantity;
	}

	public OrderItemDTO(OrderDTO order, ProductDTO product, int quantity, float finalPrice) {
		this.order = order;
		this.product = product;
		this.quantity = quantity;
		this.finalPrice = finalPrice;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrderDTO getOrder() {
		return order;
	}

	public void setOrder(OrderDTO order) {
		this.order = order;
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
	
	
	public float getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice() {
		this.finalPrice = product.getPrice()*this.quantity;
	}

	@Override
	public String toString() {
		return "OrderItem{" + 
					"id = " + id +
					", order = " + order.toString() +
					", product = " + product.toString() + 
					", quantity = " + quantity + 
					", final_price = " + finalPrice + '\'' +
					'}';
					
	}
}
