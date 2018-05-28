package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Product;

@Repository
@Transactional
public interface OrderItemDAO extends JpaRepository<OrderItem, Long>{
	
	List<OrderItem> findOrderItemsByOrder(Order order);
	
	List<OrderItem> findOrderItemsByProduct(Product product);
}
