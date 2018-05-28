package com.example.demo.service;

import java.util.List;

public interface ChefProductService {
	
	ChefProductDTO getChefProductById(Long id);
	
	List<ChefProductDTO> getChefsByProduct(ProductDTO product);
	
	List<ChefProductDTO> getProductsByChef(UserDTO chef);
	
	ChefProductDTO createChefProduct(ChefProductDTO chefProductDTO);
	
	ChefProductDTO updateChefProduct(Long id, ChefProductDTO chefProductDTO);
	
	void deleteChefProduct(Long id);
}
