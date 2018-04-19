package com.example.demo.service;

import java.io.Serializable;
import java.sql.Date;

public class LaboratoryDTO implements Serializable{
	
	private Long id;
	private int number;
	private Date date;
	public String title;
	public String curricula;
	public String description;
	
	public LaboratoryDTO() {
		
	}
	
	
	public LaboratoryDTO(int number, Date date, String title, String curricula, String description) {
		this.number = number;
		this.date = date;
		this.title = title;
		this.curricula = curricula;
		this.description = description;
	}

	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		if(number != 0)
			this.number = number;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		if(date != null)
			this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if(title != null)
			this.title = title;
	}

	public String getCurricula() {
		return curricula;
	}

	public void setCurricula(String curricula) {
		if(curricula != null)
			this.curricula = curricula;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if(description != null)
			this.description = description;
	}

	@Override
	public String toString() {
		return "Laboratory{" + 
				"number = " + number +
				", date = " +  date +
				", title = " + title +
				", curricula = " + curricula +
				", description = " + description + '\'' +
				'}' + "\n";
	}
}
