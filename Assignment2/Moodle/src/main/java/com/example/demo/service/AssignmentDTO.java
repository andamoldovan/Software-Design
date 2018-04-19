package com.example.demo.service;

import java.io.Serializable;
import java.sql.Date;

public class AssignmentDTO implements Serializable{
	
	private Long id;
	private String name;
	private String description;
	private Date date;
	private LaboratoryDTO laboratoryDTO;
	
	public AssignmentDTO() {
		
	}
	
	public AssignmentDTO(String name, String description, Date date) {
		this.name = name;
		this.description = description;
		this.date = date;
	}
	
	public AssignmentDTO(String name, String description, Date date, LaboratoryDTO laboratoryDTO) {
		this.name = name;
		this.description = description;
		this.date = date;
		this.laboratoryDTO = laboratoryDTO;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public LaboratoryDTO getLaboratoryDTO() {
		return laboratoryDTO;
	}

	public void setLaboratoryDTO(LaboratoryDTO laboratoryDTO) {
		this.laboratoryDTO = laboratoryDTO;
	}
	
	@Override
	public String toString() {
		return "Assignment{" + 
				"id = " + id +
				", name = " +  name +
				", description = " + description +
				", date = " + date +
				", laboratory_id = " + laboratoryDTO.getId() + '\'' +
				'}' + "\n";
	}
}
