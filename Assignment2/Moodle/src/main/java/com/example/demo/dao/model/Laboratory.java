package com.example.demo.dao.model;

import java.sql.Date;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "laboratory")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Laboratory {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idlaboratory_id")
	private Long id;
	
	@Column(name = "laboratory_number")
	private int number;
	
	@Column(name = "laboratory_date")
	private Date date;
	
	@Column(name = "laboratory_title")
	private String title;
	
	@Column(name = "laboratory_curricula")
	private String curricula;
	
	@Column(name = "laboratory_description")
	private String description;
	
	@ManyToMany(mappedBy = "studentAttendance", fetch = FetchType.EAGER)
	private Set<Student> laboratoryAttendances = new HashSet<>();
	
	@OneToMany(mappedBy = "laboratoryIdentifier", fetch = FetchType.EAGER)
	private Set<Assignment> assignments = new HashSet<>();
	
	public Laboratory() {
		
	}
	
	public Laboratory(int number, Date date, String title, String curricula, String description) {
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
		this.number = number;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCurricula() {
		return curricula;
	}

	public void setCurricula(String curricula) {
		this.curricula = curricula;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public Set<Student> getLaboratoryAttendances() {
		return laboratoryAttendances;
	}

	public void setLaboratoryAttendances(Set<Student> laboratoryAttendances) {
		this.laboratoryAttendances = laboratoryAttendances;
	}

	
	public Set<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(Set<Assignment> assignments) {
		this.assignments = assignments;
	}

	@Override
	public String toString() {
		return "Laboratory{" + 
				"number = " + number +
				", date = " +  date +
				", title = " + title +
				", curricula = " + curricula +
				", description = " + description + '\'' +
				'}';
	}
}