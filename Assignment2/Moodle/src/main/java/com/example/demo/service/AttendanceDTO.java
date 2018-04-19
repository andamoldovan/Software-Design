package com.example.demo.service;

import java.io.Serializable;

public class AttendanceDTO implements Serializable{
	
	private Long id;
	private StudentDTO studentDTO;
	private LaboratoryDTO laboratoryDTO;
	
	public AttendanceDTO() {
		
	}
	
	public AttendanceDTO(StudentDTO studentDTO, LaboratoryDTO laboratoryDTO) {
		this.studentDTO = studentDTO;
		this.laboratoryDTO = laboratoryDTO;
	}
	
	public AttendanceDTO(Long id, StudentDTO studentDTO, LaboratoryDTO laboratoryDTO) {
		this.id = id;
		this.studentDTO = studentDTO;
		this.laboratoryDTO = laboratoryDTO;
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

	public LaboratoryDTO getLaboratoryDTO() {
		return laboratoryDTO;
	}

	public void setLaboratoryDTO(LaboratoryDTO laboratoryDTO) {
		this.laboratoryDTO = laboratoryDTO;
	}

	@Override
	public String toString() {
		return "Attendance{" +
				"id = " + id +
				",student_id = " + studentDTO.getId() +
				", laboratory_id = " +  laboratoryDTO.getId() + '\'' +
				'}' + "\n";
	}

	
}
