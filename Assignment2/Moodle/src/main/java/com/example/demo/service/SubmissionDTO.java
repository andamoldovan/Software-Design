package com.example.demo.service;

import java.io.Serializable;

public class SubmissionDTO implements Serializable{
	
	private Long id;
	private StudentDTO studentDTO;
	private AssignmentDTO assignmentDTO;
	private String gitRepository;
	private String note;
	
	public SubmissionDTO() {
		
	}
	
	public SubmissionDTO(StudentDTO studentDTO, AssignmentDTO assignmentDTO, String gitRepository, String note) {
		this.studentDTO = studentDTO;
		this.assignmentDTO = assignmentDTO;
		this.gitRepository = gitRepository;
		this.note = note;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StudentDTO getStudentDTO() {
		return studentDTO;
	}

	public void setStudentDTO(StudentDTO studentDTO) {
		this.studentDTO = studentDTO;
	}

	public AssignmentDTO getAssignmentDTO() {
		return assignmentDTO;
	}

	public void setAssignmentDTO(AssignmentDTO assignmentDTO) {
		this.assignmentDTO = assignmentDTO;
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
				", student = " + studentDTO.getId() +
				", assignment = " + assignmentDTO.getId() + 
				", gitRepository = " + gitRepository + 
				", note = " + note + '\'' +
				'}' + "\n";
	}
}
