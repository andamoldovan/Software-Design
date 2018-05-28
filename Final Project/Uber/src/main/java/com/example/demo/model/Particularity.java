package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "particularities")
public class Particularity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "spicy")
	private int spicy;
	
	@Column(name = "gluten")
	private int gluten;
	
	@Column(name = "lactose")
	private int lactose;
	
	@Column(name = "hot")
	private int hot;
	
	@Column(name = "nuts")
	private int nuts;
	
	@Column(name = "vegan")
	private int vegan;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "particularity")
	private Product product;
	
	public Particularity() {
		
	}
	
	public Particularity(int spicy, int gluten, int lactose, int hot, int nuts, int vegan) {
		this.spicy = spicy;
		this.gluten = gluten;
		this.lactose = lactose;
		this.hot = hot;
		this.nuts = nuts;
		this.vegan = vegan;
	}
	
	public Particularity(Long id, int spicy, int gluten, int lactose, int hot, int nuts, int vegan) {
		this.id = id;
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
