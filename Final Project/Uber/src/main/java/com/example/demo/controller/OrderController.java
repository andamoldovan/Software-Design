package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.OrderDTO;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserDTO;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	private final OrderService orderService;
	private final UserService userService;
	
	@Autowired 
	public OrderController(OrderService orderService, UserService userService) {
		this.orderService = orderService;
		this.userService = userService;
	}
	
	@GetMapping()
	public List<OrderDTO> getAllOrders(){
		try {
			return orderService.getAllOrders();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{id}")
	public OrderDTO getOrderById(@RequestParam("id") Long id) {
		try {
			return orderService.getOrderById(id);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{customerId}")
	public List<OrderDTO> getOrdersByCustomer(@RequestParam("customer_id") Long customerId) {
		try {
			UserDTO customer = userService.getUserById(customerId);
			return orderService.getOrdersByCustomer(customer);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping()
	public OrderDTO createOrder(@RequestParam("customer_id") Long customerId, @RequestParam("chef_id") Long chefId) {
		try {
			UserDTO customer = userService.getUserById(customerId);
			UserDTO chef = userService.getUserById(chefId);
			System.out.println(customer.toString() + "\n\n" + chef.toString());
			OrderDTO orderDTO = new OrderDTO(customer, chef);
			return orderService.createOrder(orderDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PutMapping()
	public OrderDTO updateOrder(@RequestParam("id") Long id, @RequestParam("customer_id") Long customerId,  @RequestParam("chef_id") Long chefId) {
		try {
			UserDTO customer = userService.getUserById(customerId);
			UserDTO chef = userService.getUserById(chefId);
			OrderDTO orderDTO = new OrderDTO(customer, chef);
			return orderService.updateOrder(id, orderDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@DeleteMapping()
	public String deleteOrder(@RequestParam("id") Long id) {
		try {
			orderService.deleteOrder(id);
			return "Order at id: " + id + " was deleted"; 
		}catch(Exception e){
			e.printStackTrace();
			return "Order at id: " + id + " was not deleted";
		}
	}
	
	@PutMapping(value = "/{id, rating}")
	public OrderDTO finalizeOrder(@RequestParam("id") Long id, @RequestParam("rating") int rating) {
		try {
			return orderService.finalizeOrder(id, rating);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
