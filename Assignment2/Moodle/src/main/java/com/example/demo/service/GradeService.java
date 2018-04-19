package com.example.demo.service;

import java.util.List;

public interface GradeService {

	List<GradeDTO> getAllGrades();

	GradeDTO getGradeById(Long id);
	
	List<GradeDTO> getGradeByAssignment(AssignmentDTO assignmentDTO);
	
	GradeDTO saveGrade(GradeDTO gradeDTO);
	
	GradeDTO updateGrade(Long id, GradeDTO gradeDTO);
	
	void deleteGradeById(Long id);
	
}
