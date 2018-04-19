package com.example.demo.service;

import java.util.List;

import com.example.demo.dao.model.Assignment;

public interface AssignmentService {
	
	List<AssignmentDTO> getAllAssignments();
	
	List<AssignmentDTO> getAssignmentsForLaboratory(Long id);
	
	AssignmentDTO getAssignmentById(Long id);
	
	Assignment saveAssignment(AssignmentDTO assignmentDTO, Long lab_id);
	
	Assignment updateAssignment(Long id, Long laboratory_id, AssignmentDTO assignemntDTO);
	
	void deleteById(Long id);
}
