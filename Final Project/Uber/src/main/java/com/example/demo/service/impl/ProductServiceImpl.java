package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ProductDAO;
import com.example.demo.model.Particularity;
import com.example.demo.model.Product;
import com.example.demo.service.ParticularityDTO;
import com.example.demo.service.ProductDTO;
import com.example.demo.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	private final ProductDAO productDAO;
	
	@Autowired
	public ProductServiceImpl(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	@Override
	public List<ProductDTO> getAllProducts() {
		try {
			return transform(productDAO.findAll());
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ProductDTO getProductById(Long id) {
		try {
			return transform(productDAO.getOne(id));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ProductDTO> getProductsByParticularity(ParticularityDTO particularityDTO) {
		try {
			Particularity p = new Particularity(particularityDTO.getId(), particularityDTO.getSpicy(), particularityDTO.getGluten(), particularityDTO.getLactose(), particularityDTO.getHot(), particularityDTO.getNuts(), particularityDTO.getVegan());
			return transform(productDAO.getProductsByParticularity(p));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ProductDTO createProduct(ProductDTO productDTO) {
		try {
			return transform(productDAO.save(transform(productDTO)));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
		try {
			Product product = productDAO.getOne(id);
			Product prod = transform(productDTO);
			prod.setId(id);
			return transform(productDAO.save(prod));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteProduct(Long id) {
		productDAO.delete(productDAO.getOne(id));
	}
	
	private List<ProductDTO> transform(List<Product> list){
		List<ProductDTO> result = new ArrayList<ProductDTO>();
		ProductDTO product;
		ParticularityDTO part;
		Particularity particularity;
		
		for(Product p : list) {
			particularity = p.getParticularity();
			part = new ParticularityDTO(particularity.getSpicy(), particularity.getGluten(), particularity.getLactose(), particularity.getHot(), particularity.getNuts(), particularity.getVegan());
			part.setId(particularity.getId());
			product = new ProductDTO(p.getName(), p.getDescription(), p.getOrigin(), part, p.getPrice());
			product.setId(p.getId());
			
			result.add(product);
		}
		return result;
	}
	
	private ProductDTO transform(Product p) {
		Particularity particularity = p.getParticularity();
		ParticularityDTO part = new ParticularityDTO(particularity.getSpicy(), particularity.getGluten(), particularity.getLactose(), particularity.getHot(), particularity.getNuts(), particularity.getVegan());
		part.setId(particularity.getId());
		ProductDTO product = new ProductDTO(p.getName(), p.getDescription(), p.getOrigin(), part, p.getPrice());
		product.setId(p.getId());
		
		return product;
	}
	
	private Product transform(ProductDTO productDTO) {
		ParticularityDTO part = productDTO.getParticularityDTO();
		Particularity p = new Particularity(part.getId(), part.getSpicy(), part.getGluten(), part.getLactose(), part.getHot(), part.getNuts(), part.getVegan());
		Product product = new Product(productDTO.getId(), productDTO.getName(), productDTO.getDescription(), productDTO.getOrigin(), p, productDTO.getPrice());
		
		return product;
	}
	
}
