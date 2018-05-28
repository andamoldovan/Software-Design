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

import com.example.demo.service.ChefProductDTO;
import com.example.demo.service.ChefProductService;
import com.example.demo.service.ProductDTO;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserDTO;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/chefProduct")
public class ChefProductController {
	
	private final ChefProductService chefProductService;
	private final UserService userService;
	private final ProductService productService;
	
	@Autowired
	public ChefProductController(ChefProductService chefProductService, UserService userService, ProductService productService) {
		this.chefProductService = chefProductService;
		this.userService = userService;
		this.productService = productService;
	}
	
	@GetMapping(value = "/{id}")
	public ChefProductDTO getChefProductById(@RequestParam("id") Long id) {
		try {
			return chefProductService.getChefProductById(id);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{chef_id}")
	public List<ChefProductDTO> getChefProductsByChefId(@RequestParam("chef_id") Long chefId){
		try {
			UserDTO chefDTO = userService.getUserById(chefId);
			return chefProductService.getProductsByChef(chefDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{product_id}")
	public List<ChefProductDTO> getChefProductsByProductId(@RequestParam("product_id") Long productId){
		try {
			ProductDTO productDTO = productService.getProductById(productId);
			return chefProductService.getChefsByProduct(productDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping()
	public ChefProductDTO createChefProduct(@RequestParam("chef_id") Long chefId, @RequestParam("product_id") Long productId,  @RequestParam("quantity") int quantity) {
		try {
			UserDTO chef = userService.getUserById(chefId);
			ProductDTO product = productService.getProductById(productId);
			ChefProductDTO chPr = new ChefProductDTO(chef, product, quantity);
			return chefProductService.createChefProduct(chPr);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PutMapping()
	public ChefProductDTO updateChefProduct(@RequestParam("id") Long id, @RequestParam("chef_id") Long chefId, @RequestParam("product_id") Long productId,  @RequestParam("quantity") int quantity) {
		try {
			UserDTO chef = userService.getUserById(chefId);
			ProductDTO product = productService.getProductById(productId);
			ChefProductDTO chPr = new ChefProductDTO(chef, product, quantity);
			return chefProductService.updateChefProduct(id, chPr); 
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@DeleteMapping()
	public String deleteChefProduct(@RequestParam("id") Long id) {
		try {
			chefProductService.deleteChefProduct(id);
			return "ChefProduct at id: " + id + " was deleted";
		}catch(Exception e) {
			e.printStackTrace();
			return "ChefProdcut at id: " + id + " could not be deleted";
		}
	}
}
