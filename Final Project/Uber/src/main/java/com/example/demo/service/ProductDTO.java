package com.example.demo.service;

import java.io.Serializable;

import com.example.demo.model.Particularity;

public class ProductDTO implements Serializable{
	
	private Long id;
	private String name;
	private String description;
	private String origin;
	private ParticularityDTO particularityDTO;
	private float price;
	
	public ProductDTO() {
		
	}
	
	public ProductDTO(String name, String description, String origin, ParticularityDTO particularityDTO, float price) { 
		this.name = name;
		this.description = description;
		this.origin = origin;
		this.particularityDTO = particularityDTO;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public ParticularityDTO getParticularityDTO() {
		return particularityDTO;
	}

	public void setParticularityDTO(ParticularityDTO particularityDTO) {
		this.particularityDTO = particularityDTO;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "User{" + 
					"id = " + id +
					", name = " + name +
					", description = " + description +
					", origin = " + origin +
					", particulatiry = " + particularityDTO.toString() +
					", price = " + price + '\'' +
					'}';
					
	}
}
