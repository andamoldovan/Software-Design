package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.OrderDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.service.OrderDTO;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserDTO;

@Service
public class OrderServiceImpl implements OrderService{

	private final OrderDAO orderDAO;
	
	@Autowired
	public OrderServiceImpl(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}
	
	@Override
	public List<OrderDTO> getAllOrders() {
		try {
			return transform(orderDAO.findAll());
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public OrderDTO getOrderById(Long id) {
		try {
			return transform(orderDAO.getOne(id));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<OrderDTO> getOrdersByCustomer(UserDTO customer){
		try {
			User cust = new User(customer.getId(), customer.getEmail(), customer.getPassword(), customer.getName(), customer.getAddress(), customer.getPhone(), customer.getIban());
			return transform(orderDAO.getOrdersByCustomer(cust));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<OrderDTO> getOrdersByChef(UserDTO chef) {
		try {
			User user = new User(chef.getId(), chef.getEmail(), chef.getPassword(), chef.getName(), chef.getAddress(), chef.getPhone(), chef.getIban());
			return transform(orderDAO.getOrdersByChef(user));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public OrderDTO createOrder(OrderDTO orderDTO) {
		try {
			orderDTO.setFinalizeOrder("NO");
			orderDTO.setRating(0);
			Order order = orderDAO.save(transform(orderDTO));
			return transform(order);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
		try {
			Order order = orderDAO.getOne(id);
			Order newOrd = transform(orderDTO);
			newOrd.setId(order.getId());
			newOrd.setConfirmed(orderDTO.getConfirmed());
			newOrd.setFinalizeOrder(orderDTO.getFinalizeOrder());
			newOrd.setRating(orderDTO.getRating());
			return transform(orderDAO.save(newOrd));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteOrder(Long id) {
		orderDAO.delete(orderDAO.getOne(id));
		
	}
	

	@Override
	public OrderDTO finalizeOrder(Long id, int rating) {
		try {
			Order order = orderDAO.getOne(id);
			order.setFinalizeOrder("YES");
			order.setRating(rating);
			return transform(orderDAO.save(order));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private List<OrderDTO> transform(List<Order> list){
		List<OrderDTO> result = new ArrayList<OrderDTO>();
		User customer, chef;
		UserDTO customerDTO, chefDTO;
		OrderDTO orderDTO;
		
		for(Order order : list) {
			customer = order.getCustomer();
			chef = order.getChef();
			customerDTO = new UserDTO(customer.getEmail(), customer.getPassword(), customer.getName(), customer.getAddress(), customer.getPhone(), customer.getIban());
			customerDTO.setId(customer.getId());
			chefDTO = new UserDTO(chef.getEmail(), chef.getPassword(), chef.getName(), chef.getAddress(), chef.getPhone(), chef.getIban());
			chefDTO.setId(chef.getId());
			orderDTO = new OrderDTO(customerDTO, chefDTO);
			orderDTO.setId(order.getId());
			orderDTO.setFinalizeOrder(order.getFinalizeOrder());
			orderDTO.setRating(order.getRating());
			orderDTO.setConfirmed(order.getConfirmed());
			result.add(orderDTO);
		}
		return result;
	}
	
	private OrderDTO transform(Order order) {
		User customer = order.getCustomer();
		User chef = order.getChef();
		UserDTO customerDTO = new UserDTO(customer.getEmail(), customer.getPassword(), customer.getName(), customer.getAddress(), customer.getPhone(), customer.getIban());
		customerDTO.setId(customer.getId());
		UserDTO chefDTO = new UserDTO(chef.getEmail(), chef.getPassword(), chef.getName(), chef.getAddress(), chef.getPhone(), chef.getIban());
		chefDTO.setId(chef.getId());
		
		OrderDTO orderDTO = new OrderDTO(customerDTO, chefDTO);
		orderDTO.setId(order.getId());
		orderDTO.setFinalizeOrder(order.getFinalizeOrder());
		orderDTO.setRating(order.getRating());
		orderDTO.setConfirmed(order.getConfirmed());
		return orderDTO;
	}
	
	private Order transform(OrderDTO orderDTO) {
		UserDTO customerDTO = orderDTO.getCustomer();
		UserDTO chefDTO = orderDTO.getChef();
		User customer = new User(customerDTO.getId(), customerDTO.getEmail(), customerDTO.getPassword(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getPhone(), customerDTO.getIban());
		User chef = new User(chefDTO.getId(), chefDTO.getEmail(), chefDTO.getPassword(), chefDTO.getName(), chefDTO.getAddress(), chefDTO.getPhone(), chefDTO.getIban());
		Order order = new Order();
		order.setCustomer(customer);
		order.setChef(chef);
		return order;
	}

	


}
