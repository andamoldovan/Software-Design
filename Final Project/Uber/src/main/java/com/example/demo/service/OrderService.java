package com.example.demo.service;

import java.util.List;

public interface OrderService {
	
	List<OrderDTO> getAllOrders();
	
	OrderDTO getOrderById(Long id);

	List<OrderDTO> getOrdersByCustomer(UserDTO customer);
	
	List<OrderDTO> getOrdersByChef(UserDTO chef);
	
	OrderDTO finalizeOrder(Long id, int rating);
	
	OrderDTO createOrder(OrderDTO orderDTO);
	
	OrderDTO updateOrder(Long id, OrderDTO orderDTO);
	
	void deleteOrder(Long id);
}
