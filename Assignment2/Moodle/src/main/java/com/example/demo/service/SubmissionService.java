package com.example.demo.service;

import java.util.List;

import com.example.demo.dao.model.Submission;

public interface SubmissionService {
	
	List<SubmissionDTO> getAllSubmissions();
	
	SubmissionDTO getSubmissionById(Long id);
	
	Submission saveSubmission(SubmissionDTO submissionDTO);
	
	Submission updateAssignment(Long id, SubmissionDTO SubmissionDTO);
	
	void deleteById(Long id);
}
