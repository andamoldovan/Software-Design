package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ChefProductDAO;
import com.example.demo.model.ChefProduct;
import com.example.demo.model.Particularity;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.ChefProductDTO;
import com.example.demo.service.ChefProductService;
import com.example.demo.service.ParticularityDTO;
import com.example.demo.service.ProductDTO;
import com.example.demo.service.UserDTO;

@Service
public class ChefProductServiceImpl implements ChefProductService{
	
	private final ChefProductDAO chefProductDAO;
	
	@Autowired
	public ChefProductServiceImpl(ChefProductDAO chefProductDAO) {
		this.chefProductDAO = chefProductDAO;
	}

	@Override
	public ChefProductDTO getChefProductById(Long id) {
		try {
			return transform(chefProductDAO.getOne(id));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ChefProductDTO> getChefsByProduct(ProductDTO product) {
		try {
			ParticularityDTO partDTO = product.getParticularityDTO();
			Particularity part = new Particularity(partDTO.getId(), partDTO.getSpicy(), partDTO.getGluten(), partDTO.getLactose(), partDTO.getHot(), partDTO.getNuts(), partDTO.getVegan());
			Product p = new Product(product.getId(), product.getName(), product.getDescription(), product.getOrigin(), part, product.getPrice());
			List<ChefProduct> ch = chefProductDAO.findCheProductByProduct(p);
			List<ChefProductDTO> result = new ArrayList<ChefProductDTO>();
			for(ChefProduct c : ch) {
				ChefProductDTO prod = transform(c);
				prod.setQuantity(c.getQuantity());
				result.add(prod);
			}
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ChefProductDTO> getProductsByChef(UserDTO chef) {
		try {
			User c = new User(chef.getId(), chef.getEmail(), chef.getPassword(), chef.getName(), chef.getAddress(), chef.getPhone(), chef.getIban());
			return transform(chefProductDAO.findChefProductByChef(c));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ChefProductDTO createChefProduct(ChefProductDTO chefProductDTO) {
		try {
			return transform(chefProductDAO.save(transform(chefProductDTO)));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ChefProductDTO updateChefProduct(Long id, ChefProductDTO chefProductDTO) {
		try {
			ChefProduct c = transform(chefProductDTO);
			c.setId(id);
			return transform(chefProductDAO.save(c));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteChefProduct(Long id) {
		chefProductDAO.delete(chefProductDAO.getOne(id));
	}
	
	private List<ChefProductDTO> transform(List<ChefProduct> list){
		List<ChefProductDTO> result = new ArrayList<ChefProductDTO>();
		User chef;
		Product product;
		UserDTO chefDTO;
		ProductDTO productDTO;
		Particularity p;
		ParticularityDTO part;
		ChefProductDTO chPr;
		
		for(ChefProduct c : list) {
			chef = c.getChef();
			chefDTO = new UserDTO(chef.getEmail(), chef.getPassword(), chef.getName(), chef.getAddress(), chef.getPhone(), chef.getIban());
			chefDTO.setId(chef.getId());
			product = c.getProduct();
			p = product.getParticularity();
			part = new ParticularityDTO(p.getSpicy(), p.getGluten(), p.getLactose(), p.getHot(), p.getNuts(), p.getVegan());
			part.setId(p.getId());
			
			productDTO = new ProductDTO(product.getName(), product.getDescription(), product.getOrigin(), part, product.getPrice());
			productDTO.setId(product.getId());
			
			chPr = new ChefProductDTO(chefDTO, productDTO, c.getQuantity());
			chPr.setId(c.getId());
			result.add(chPr);
		}
		return result;
	}
	
	private ChefProductDTO transform(ChefProduct c) {
		User chef = c.getChef();
		UserDTO chefDTO = new UserDTO(chef.getEmail(), chef.getPassword(), chef.getName(), chef.getAddress(), chef.getPhone(), chef.getIban());
		chefDTO.setId(chef.getId());
		Product product = c.getProduct();
		Particularity p = product.getParticularity();
		ParticularityDTO part = new ParticularityDTO(p.getSpicy(), p.getGluten(), p.getLactose(), p.getHot(), p.getNuts(), p.getVegan());
		part.setId(p.getId());
		ProductDTO productDTO = new ProductDTO(product.getName(), product.getDescription(), product.getOrigin(), part, product.getPrice());
		productDTO.setId(product.getId());
		
		ChefProductDTO chPr = new ChefProductDTO(chefDTO, productDTO, c.getQuantity());
		chPr.setId(c.getId());
		return  chPr;
	}
	
	private ChefProduct transform(ChefProductDTO c) {
		UserDTO chefDTO = c.getChef();
		User chef = new User(chefDTO.getId(), chefDTO.getEmail(), chefDTO.getPassword(), chefDTO.getName(), chefDTO.getAddress(), chefDTO.getPhone(), chefDTO.getIban());
		ProductDTO prod = c.getProduct();
		ParticularityDTO partDTO = prod.getParticularityDTO();
		Particularity part = new Particularity(partDTO.getId(), partDTO.getSpicy(), partDTO.getGluten(), partDTO.getLactose(), partDTO.getHot(), partDTO.getNuts(), partDTO.getVegan());
		Product p = new Product(prod.getId(), prod.getName(), prod.getDescription(), prod.getOrigin(), part, prod.getPrice());
		
		ChefProduct ch = new ChefProduct();
		ch.setChef(chef);
		ch.setProduct(p);
		ch.setQuantity(c.getQuantity());
		return ch;
	}
}
