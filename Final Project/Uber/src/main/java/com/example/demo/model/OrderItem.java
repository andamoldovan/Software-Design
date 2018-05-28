package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "final_price")
	private float finalPrice;
	
	public OrderItem() {
		
	}
	
	public OrderItem(Long id, Order order, Product product, int quantity) {
		this.id = id;
		this.order = order;
		this.product = product;
		this.quantity = quantity;
	}
	
	public OrderItem(Long id, Order order, Product product, int quantity, float finalPrice) {
		this.id = id;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
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

	public void setFinalPrice(float finalPrice) {
		this.finalPrice = finalPrice;
	}

	@Override
	public String toString() {
		return "OrderItem{" + 
					"id = " + id +
					", order = " + order.toString() +
					", product = " + product.toString() + 
					", qyantity = " + quantity +
					", final_price = " + finalPrice +'\'' +
					'}';
					
	}
}
