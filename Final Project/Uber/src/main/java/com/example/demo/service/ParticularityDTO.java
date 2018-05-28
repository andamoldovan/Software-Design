package com.example.demo.service;

import java.io.Serializable;

public class ParticularityDTO implements Serializable{
	
	private Long id;
	private int spicy;
	private int gluten;
	private int lactose;
	private int hot;
	private int nuts;
	private int vegan;
	
	public ParticularityDTO() {
		
	}
	
	public ParticularityDTO(int spicy, int gluten, int lactose, int hot, int nuts, int vegan) {
		this.spicy = spicy;
		this.gluten = gluten;
		this.lactose = lactose;
		this.hot = hot;
		this.nuts = nuts;
		this.vegan = vegan;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSpicy() {
		return spicy;
	}

	public void setSpicy(int spicy) {
		this.spicy = spicy;
	}

	public int getGluten() {
		return gluten;
	}

	public void setGluten(int gluten) {
		this.gluten = gluten;
	}

	public int getLactose() {
		return lactose;
	}

	public void setLactose(int lactose) {
		this.lactose = lactose;
	}

	public int getHot() {
		return hot;
	}

	public void setHot(int hot) {
		this.hot = hot;
	}

	public int getNuts() {
		return nuts;
	}

	public void setNuts(int nuts) {
		this.nuts = nuts;
	}

	public int getVegan() {
		return vegan;
	}

	public void setVegan(int vegan) {
		this.vegan = vegan;
	}
	
	@Override
	public String toString() {
		return "Particularity{" + 
					"id = " + id +
					", spicy = " + spicy +
					", gluten = " + gluten +
					", lactose = " + lactose +
					", hot = " + hot +
					", nuts = " + nuts +
					", vegan = " + vegan + '\'' +
					'}';
					
	}
}
