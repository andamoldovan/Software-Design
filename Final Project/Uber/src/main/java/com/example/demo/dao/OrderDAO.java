package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Order;
import com.example.demo.model.User;

@Repository
@Transactional(rollbackFor = Exception.class)
public interface OrderDAO extends JpaRepository<Order, Long>{
	
	List<Order> getOrdersByCustomer(User customer);
	
	List<Order> getOrdersByChef(User chef);
}
