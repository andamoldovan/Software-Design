package com.example.demo.dao.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "assignment")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Assignment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "assignment_id")
	private Long id;
	
	@Column(name = "assignment_name")
	private String name;
	
	@Column(name = "assignment_description")
	private String description;
	
	@Column(name = "assignment_date")
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "laboratory_id_assignment", nullable = false)
	private Laboratory laboratoryIdentifier;
	
	@OneToMany(mappedBy = "assignmentIdentifier", fetch = FetchType.EAGER)
	private Set<Grade> grades = new HashSet<>();
	
	@ManyToMany(mappedBy = "studentSubmissions", fetch = FetchType.EAGER)
	private Set<Student> AssignmentSubmissionss = new HashSet<>();
	
	public Assignment() {
		
	}
	
	public Assignment(String name, String description, Date date, Laboratory laboratoryIdentifier) {
		this.name = name;
		this.description = description;
		this.date = date;
		this.laboratoryIdentifier = laboratoryIdentifier;
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

	public Laboratory getLaboratoryIdentifier() {
		return laboratoryIdentifier;
	}

	public void setLaboratoryIdentifier(Laboratory laboratoryIdentifier) {
		this.laboratoryIdentifier = laboratoryIdentifier;
	}
	
	public Set<Grade> getGrades() {
		return grades;
	}

	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}
	
	

	@Override 
	public String toString() {
		return "Assignment{" + 
				"id = " + id +
				", name = " +  name +
				", description = " + description +
				", date = " + date +
				", laboratory_id = " + laboratoryIdentifier + '\'' +
				'}';
	}
}
