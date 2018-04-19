package com.example.demo.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.GradeDAO;
import com.example.demo.dao.model.Assignment;
import com.example.demo.dao.model.Grade;
import com.example.demo.dao.model.Laboratory;
import com.example.demo.dao.model.Student;
import com.example.demo.service.AssignmentDTO;
import com.example.demo.service.GradeDTO;
import com.example.demo.service.GradeService;
import com.example.demo.service.LaboratoryDTO;
import com.example.demo.service.StudentDTO;

@Service
public class GradeServiceImpl implements GradeService{

	private final GradeDAO gradeDAO;
	
	@Autowired
	public GradeServiceImpl(GradeDAO gradeDAO) {
		this.gradeDAO = gradeDAO;
	}
	
	
	@Override
	public List<GradeDTO> getAllGrades() {
		try {
			return transform(gradeDAO.findAll());
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public GradeDTO getGradeById(Long id) {
		try {
			Grade grade = gradeDAO.getOne(id);
			return transform(grade);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	@Override
	public List<GradeDTO> getGradeByAssignment(AssignmentDTO assignmentDTO) {
		LaboratoryDTO labDTO = assignmentDTO.getLaboratoryDTO();
		Laboratory lab = new Laboratory(labDTO.getNumber(), labDTO.getDate(), labDTO.getTitle(), labDTO.getCurricula(), labDTO.getDescription());
		lab.setId(labDTO.getId());
		Assignment assignment = new Assignment(assignmentDTO.getName(), assignmentDTO.getDescription(), assignmentDTO.getDate(), lab);
		assignment.setId(assignmentDTO.getId());
		
		try {
			return transform(gradeDAO.findGradesByAssignmentIdentifier(assignment));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public GradeDTO saveGrade(GradeDTO gradeDTO) {
		Grade newGrade = transform(gradeDTO);
		try {
			return transform(gradeDAO.save(newGrade));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public GradeDTO updateGrade(Long id, GradeDTO gradeDTO) {
		Grade grade = gradeDAO.getOne(gradeDTO.getId());
		Grade updated = transform(gradeDTO);
		grade.setAssignmentIdentifier(updated.getAssignmentIdentifier());
		grade.setGrade(updated.getGrade());
		grade.setStudentIdentifier(updated.getStudentIdentifier());
		grade.setTimesSubmitted(updated.getTimesSubmitted());
		try {
			return transform(gradeDAO.save(grade));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteGradeById(Long id) {
		try {
			gradeDAO.deleteById(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<GradeDTO> transform(List<Grade> list){
		List<GradeDTO> result = new ArrayList<GradeDTO>();
		GradeDTO gradeDTO;
		Student s;
		Assignment a;
		StudentDTO studentDTO;
		AssignmentDTO assignmentDTO;
		for(Grade g : list) {
			s = new Student();
			a = new Assignment();
			s = g.getStudentIdentifier();
			a = g.getAssignmentIdentifier();
			
			studentDTO = new StudentDTO(s.getEmail(), s.getGroup(), s.getHobby(), s.getFullName(), s.getPassword());
			studentDTO.setId(s.getStudentId());
			assignmentDTO = new AssignmentDTO(a.getName(), a.getDescription(), a.getDate(),
						new LaboratoryDTO(a.getLaboratoryIdentifier().getNumber(), a.getLaboratoryIdentifier().getDate(),
								a.getLaboratoryIdentifier().getTitle(), a.getLaboratoryIdentifier().getCurricula(), a.getLaboratoryIdentifier().getDescription()));
			assignmentDTO.setId(a.getId());
			gradeDTO = new GradeDTO(g.getTimesSubmitted(), g.getGrade());
			gradeDTO.setAssignment(assignmentDTO);
			gradeDTO.setStudent(studentDTO);
			gradeDTO.setId(g.getId());
			
			result.add(gradeDTO);
		}
		return result;
	}
	
	private GradeDTO transform(Grade grade) {
		GradeDTO gradeDTO;
		Student s = grade.getStudentIdentifier();
		Assignment a = grade.getAssignmentIdentifier();
		StudentDTO studentDTO = new StudentDTO(s.getEmail(), s.getGroup(), s.getHobby(), s.getFullName(), s.getPassword());
		studentDTO.setId(s.getStudentId());
		AssignmentDTO assignmentDTO = new AssignmentDTO(a.getName(), a.getDescription(), a.getDate(),
				new LaboratoryDTO(a.getLaboratoryIdentifier().getNumber(), a.getLaboratoryIdentifier().getDate(),
						a.getLaboratoryIdentifier().getTitle(), a.getLaboratoryIdentifier().getCurricula(), a.getLaboratoryIdentifier().getDescription()));
		assignmentDTO.setId(a.getId());
		
		gradeDTO = new GradeDTO(grade.getTimesSubmitted(), grade.getGrade());
		gradeDTO.setAssignment(assignmentDTO);
		gradeDTO.setStudent(studentDTO);
		gradeDTO.setId(grade.getId());
		
		return gradeDTO;
	}
	
	private Grade transform(GradeDTO grade) {
		Grade g;
		StudentDTO s = grade.getStudent();
		AssignmentDTO a = grade.getAssignment();
		Student student = new Student(s.getEmail(), s.getGroup(), s.getHobby(), s.getFullName(), s.getPassword(), null);
		student.setStudentId(s.getId());
		Assignment assignment = new Assignment(a.getName(), a.getDescription(), a.getDate(),
				new Laboratory(a.getLaboratoryDTO().getNumber(), a.getLaboratoryDTO().getDate(),
						a.getLaboratoryDTO().getTitle(), a.getLaboratoryDTO().getCurricula(), a.getLaboratoryDTO().getDescription()));
		assignment.setId(a.getId());
		
		g = new Grade(grade.getTimesSumbitted(), grade.getGrade());
		g.setAssignmentIdentifier(assignment);
		g.setStudentIdentifier(student);
		g.setId(grade.getId());
		return g;
		
	}



}
