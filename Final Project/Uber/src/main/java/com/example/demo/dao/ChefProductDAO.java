package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.ChefProduct;
import com.example.demo.model.Product;
import com.example.demo.model.User;

@Repository
@Transactional
public interface ChefProductDAO extends JpaRepository<ChefProduct, Long>{
	
	List<ChefProduct> findCheProductByProduct(Product product);
	
	List<ChefProduct> findChefProductByChef(User chef);
}
