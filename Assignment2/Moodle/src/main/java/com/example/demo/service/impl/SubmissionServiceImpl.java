package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.SubmissionDAO;
import com.example.demo.dao.model.Assignment;
import com.example.demo.dao.model.Laboratory;
import com.example.demo.dao.model.Student;
import com.example.demo.dao.model.Submission;
import com.example.demo.service.AssignmentDTO;
import com.example.demo.service.LaboratoryDTO;
import com.example.demo.service.StudentDTO;
import com.example.demo.service.SubmissionDTO;
import com.example.demo.service.SubmissionService;

@Service
public class SubmissionServiceImpl implements SubmissionService{
	
	public final SubmissionDAO submissionDAO;
	
	@Autowired
	public SubmissionServiceImpl(SubmissionDAO submissionDAO) {
		this.submissionDAO = submissionDAO;
	}
	
	@Override
	public List<SubmissionDTO> getAllSubmissions() {
		try {
			return transform(submissionDAO.findAll());
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public SubmissionDTO getSubmissionById(Long id) {
		try {
			return transform(submissionDAO.getOne(id));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Submission saveSubmission(SubmissionDTO submissionDTO) {
		try {
			return submissionDAO.save(transform(submissionDTO));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Submission updateAssignment(Long id, SubmissionDTO submissionDTO) {
		Submission submission = submissionDAO.getOne(id);
		Submission subb = transform(submissionDTO);
		submission.setAssignmentReff(subb.getAssignmentReff());
		submission.setGitRepository(subb.getGitRepository());
		submission.setNote(subb.getNote());
		submission.setStudentReff(subb.getStudentReff());
		try {
			return submissionDAO.save(submission);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteById(Long id) {
		submissionDAO.deleteById(id);
	}
	
	public List<SubmissionDTO> transform(List<Submission> list){
		List<SubmissionDTO> result = new ArrayList<>();
		SubmissionDTO subb;
		Laboratory lab;
		LaboratoryDTO labDTO;
		Student s;
		StudentDTO sDTO;
		Assignment a;
		AssignmentDTO aDTO;
		
		for(Submission submission : list) {
			lab = submission.getAssignmentReff().getLaboratoryIdentifier();
			labDTO = new LaboratoryDTO(lab.getNumber(), lab.getDate(), lab.getTitle(), lab.getCurricula(), lab.getDescription());
			labDTO.setId(lab.getId());
			
			s = submission.getStudentReff();
			sDTO = new StudentDTO(s.getEmail(), s.getGroup(), s.getHobby(), s.getFullName(), s.getPassword());
			sDTO.setId(s.getStudentId());
			a = submission.getAssignmentReff();
			aDTO = new AssignmentDTO(a.getName(), a.getDescription(), a.getDate(), labDTO);
			aDTO.setId(a.getId());
			
			subb = new SubmissionDTO();
			subb.setId(submission.getId());
			subb.setGitRepository(submission.getGitRepository());
			subb.setNote(submission.getNote());
			subb.setAssignmentDTO(aDTO);
			subb.setStudentDTO(sDTO);
			
			result.add(subb);
		}
		return result;
	}
	
	
	private SubmissionDTO transform(Submission submission) {
		SubmissionDTO subb = new SubmissionDTO();
		
		LaboratoryDTO labDTO;
		Laboratory lab = submission.getAssignmentReff().getLaboratoryIdentifier();
		labDTO = new LaboratoryDTO(lab.getNumber(), lab.getDate(), lab.getTitle(), lab.getCurricula(), lab.getDescription());
		labDTO.setId(lab.getId());
		
		StudentDTO sDTO;
		Student s = submission.getStudentReff();
		sDTO = new StudentDTO(s.getEmail(), s.getGroup(), s.getHobby(), s.getFullName(), s.getPassword());
		sDTO.setId(s.getStudentId());
		
		AssignmentDTO aDTO;
		Assignment a = submission.getAssignmentReff();
		aDTO = new AssignmentDTO(a.getName(), a.getDescription(), a.getDate(), labDTO);
		aDTO.setId(a.getId());
		
		subb.setId(submission.getId());
		subb.setGitRepository(submission.getGitRepository());
		subb.setNote(submission.getNote());
		subb.setAssignmentDTO(aDTO);
		subb.setStudentDTO(sDTO);
		
		return subb;
	}
	
	private Submission transform(SubmissionDTO submissionDTO) {
		Submission subb = new Submission();
		
		Laboratory lab;
		LaboratoryDTO labDTO = submissionDTO.getAssignmentDTO().getLaboratoryDTO();
		lab = new Laboratory(labDTO.getNumber(), labDTO.getDate(), labDTO.getTitle(), labDTO.getCurricula(), labDTO.getDescription());
		lab.setId(labDTO.getId());
		
		Student s;
		StudentDTO sDTO = submissionDTO.getStudentDTO();
		s = new Student(sDTO.getEmail(), sDTO.getGroup(), sDTO.getHobby(), sDTO.getFullName(), sDTO.getPassword(), null);
		s.setStudentId(sDTO.getId());
		
		Assignment a;
		AssignmentDTO aDTO = submissionDTO.getAssignmentDTO();
		a = new Assignment(aDTO.getName(), aDTO.getDescription(), aDTO.getDate(), lab);
		a.setId(aDTO.getId());
		
		subb.setId(submissionDTO.getId());
		subb.setGitRepository(submissionDTO.getGitRepository());
		subb.setNote(submissionDTO.getNote());
		subb.setAssignmentReff(a);
		subb.setStudentReff(s);
		
		return subb;
	}
}
