package com.example.demo.service;

import java.util.List;

import com.example.demo.model.OrderItem;

public interface OrderItemService {
	
	OrderItemDTO getOrderItemById(Long id);
	
	List<OrderItemDTO> getOrderItemsByOrder(OrderDTO orderDTO);
	
	List<OrderItemDTO> getOrderItemsByProduct(ProductDTO productDTO);
	
	OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO);
	
	OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO);
	
	void deleteOrderItem(Long id);
}
