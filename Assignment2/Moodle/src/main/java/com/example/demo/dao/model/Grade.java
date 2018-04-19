package com.example.demo.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "grade")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Grade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "grade_id")
	private Long id;
	
	@Column(name = "submitted")
	private int timesSubmitted;
	
	@Column(name = "grade")
	private int grade;
	
	@ManyToOne
	@JoinColumn(name = "student_id_grade", nullable = false)
	private Student studentIdentifier;
	
	@ManyToOne
	@JoinColumn(name = "assignment_id", nullable = false)
	private Assignment assignmentIdentifier;
	
	public Grade() {
		
	}
	
	public Grade(int timesSubmitted, int grade) {
		this.timesSubmitted = timesSubmitted;
		this.grade = grade;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getTimesSubmitted() {
		return timesSubmitted;
	}

	public void setTimesSubmitted(int timesSubmitted) {
		this.timesSubmitted = timesSubmitted;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Student getStudentIdentifier() {
		return studentIdentifier;
	}

	public void setStudentIdentifier(Student studentIdentifier) {
		this.studentIdentifier = studentIdentifier;
	}

	public Assignment getAssignmentIdentifier() {
		return assignmentIdentifier;
	}

	public void setAssignmentIdentifier(Assignment assignmentIdentifier) {
		this.assignmentIdentifier = assignmentIdentifier;
	}
	
	@Override 
	public String toString() {
		return "Grade{" + 
				"id = " + id +
				", student = " +  studentIdentifier +
				", assignment = "  + assignmentIdentifier + 
				", timesSubmitted = " + timesSubmitted +
				", grade = " + grade + '\'' +
				'}';
	}
}
