package com.example.demo.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "submission")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Submission {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "submission_id")
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "submission_student_id", nullable = false)
	private Student studentReff;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "submission_assignment_id", nullable = false)
	private Assignment assignmentReff;
	
	@Column(name = "git_repository")
	private String gitRepository;
	
	@Column(name = "note")
	private String note;
	
	
	public Submission() {
		
	}
	
	public Submission(Student studentReff, Assignment assignmentReff, String gitRepository, String note) {
		this.studentReff = studentReff;
		this.assignmentReff = assignmentReff;
		this.gitRepository = gitRepository;
		this.note = note;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Student getStudentReff() {
		return studentReff;
	}

	public void setStudentReff(Student studentReff) {
		this.studentReff = studentReff;
	}

	public Assignment getAssignmentReff() {
		return assignmentReff;
	}

	public void setAssignmentReff(Assignment assignmentReff) {
		this.assignmentReff = assignmentReff;
	}

	public String getGitRepository() {
		return gitRepository;
	}

	public void setGitRepository(String gitRepository) {
		this.gitRepository = gitRepository;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@Override
	public String toString() {
		return "Submission{" + 
				"id = " + id +
				", student = " + studentReff +
				", assignment = " + assignmentReff + 
				", gitRepository = " + gitRepository + 
				", note = " + note + '\'' +
				'}';
	}
}
