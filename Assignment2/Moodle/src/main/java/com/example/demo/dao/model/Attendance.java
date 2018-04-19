package com.example.demo.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "attendance")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Attendance {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "attendance_id")
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id", nullable = false)
	private Student studentReff;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "laboratory_id", nullable = false)
	private Laboratory laboratoryReff;

	public Attendance() {
		
	}
	
	public Attendance(Student student, Laboratory laboratory) {
		this.studentReff = student;
		this.laboratoryReff = laboratory;
	}
	
	public Student getStudent() {
		return studentReff;
	}

	public void setStudent(Student studentReff) {
		this.studentReff = studentReff;
	}

	public Laboratory getLaboratory() {
		return laboratoryReff;
	}

	public void setLaboratory(Laboratory laboratoryReff) {
		this.laboratoryReff = laboratoryReff;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	@Override
	public String toString() {
		return "Attendance{" + 
				"id = " + id +
				", student = " + studentReff +
				", laboratory = " +  laboratoryReff + '\'' +
				'}';
	}
}
