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

import com.example.demo.service.ParticularityDTO;
import com.example.demo.service.ParticularityService;
import com.example.demo.service.ProductDTO;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	private final ProductService productService;
	private final ParticularityService particularityService;
	
	@Autowired
	public ProductController(ProductService productService, ParticularityService particularityService) {
		this.productService = productService;
		this.particularityService = particularityService;
	}
	
	@GetMapping()
	public List<ProductDTO> getAllProducts(){
		try {
			return productService.getAllProducts();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{id}")
	public ProductDTO getProductById(@RequestParam("id") Long id) {
		try {
			return productService.getProductById(id);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{particularityId}")
	public List<ProductDTO> getProductsByParticularityId(@RequestParam("particularity_id") Long particularityId){
		try {
			ParticularityDTO particularityDTO = particularityService.getParticularityById(particularityId);
			return productService.getProductsByParticularity(particularityDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping()
	public ProductDTO createProduct(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("origin") String origin, @RequestParam("particularity_id") Long particularity_id, @RequestParam("price") float price) {
		try {
			ParticularityDTO particularityDTO = particularityService.getParticularityById(particularity_id);
			ProductDTO product = new ProductDTO(name, description, origin, particularityDTO, price);
			return productService.createProduct(product);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PutMapping()
	public ProductDTO updateProduct(@RequestParam("id") Long id, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("origin") String origin, @RequestParam("particularity_id") Long particularity_id, @RequestParam("price") float price) {
		try {
			ParticularityDTO particularityDTO = particularityService.getParticularityById(particularity_id);
			ProductDTO product = new ProductDTO(name, description, origin, particularityDTO, price);
			return productService.updateProduct(id, product);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@DeleteMapping()
	public String deleteProduct(@RequestParam("id") Long id) {
		try {
			productService.deleteProduct(id);
			return "Product at id: " + id + " was deletd";
		}catch(Exception e) {
			e.printStackTrace();
			return "Product at id: " + id + " could not be deleted";
		}
	}
}
