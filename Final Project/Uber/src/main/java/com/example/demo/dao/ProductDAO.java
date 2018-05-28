package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Particularity;
import com.example.demo.model.Product;

@Repository
@Transactional
public interface ProductDAO extends JpaRepository<Product, Long>{
	
	List<Product> getProductsByParticularity(Particularity particularity);
}
