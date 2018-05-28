package com.example.demo.service;

import java.util.List;

public interface ProductService {
	
	List<ProductDTO> getAllProducts();
	
	ProductDTO getProductById(Long id);
	
	List<ProductDTO> getProductsByParticularity(ParticularityDTO particularityDTO);
	
	ProductDTO createProduct(ProductDTO productDTO);
	
	ProductDTO updateProduct(Long id, ProductDTO productDTO);
	
	void deleteProduct(Long id);
}
