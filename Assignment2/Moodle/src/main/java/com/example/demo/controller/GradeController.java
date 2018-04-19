package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.AssignmentDTO;
import com.example.demo.service.AssignmentService;
import com.example.demo.service.GradeDTO;
import com.example.demo.service.GradeService;
import com.example.demo.service.StudentDTO;
import com.example.demo.service.StudentService;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/grades")
public class GradeController {
	
	public final GradeService gradeService;
	public final StudentService studentService;
	public final AssignmentService assignmentService;
	
	@Autowired
	public GradeController(GradeService gradeService, StudentService studentService, AssignmentService assignmentService) {
		this.gradeService = gradeService;
		this.studentService = studentService;
		this.assignmentService = assignmentService;
	}
	
	
	@GetMapping(value = "/{id}")
	@ResponseBody
	public GradeDTO getGradeById(@RequestParam("id") Long grade_id) {
		try {
			return gradeService.getGradeById(grade_id);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{assignmentid}")
	@ResponseBody
	public List<GradeDTO> getGradeByAssignment(@RequestParam("assignmentId") Long assignment_id){
		AssignmentDTO assignmentDTO = assignmentService.getAssignmentById(assignment_id);
		try {
			return gradeService.getGradeByAssignment(assignmentDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping()
	public GradeDTO createGrade(@RequestParam("studentId") Long student_id, @RequestParam("AssignmentId") Long assignment_id, @RequestParam("grade") int grade) {
		StudentDTO student = studentService.getStudentById(student_id);
		AssignmentDTO assignment = assignmentService.getAssignmentById(assignment_id);
		GradeDTO gradeDTO = new GradeDTO(1, grade);
		gradeDTO.setAssignment(assignment);
		gradeDTO.setStudent(student);
		try {
			return gradeService.saveGrade(gradeDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PutMapping()
	public GradeDTO updateGrade(@RequestParam("id") Long grade_id, @RequestParam("studentId") Long student_id, @RequestParam("assignmentId") Long assignment_id, @RequestParam("grade") int grade) {
		StudentDTO student = studentService.getStudentById(student_id);
		AssignmentDTO assignment = assignmentService.getAssignmentById(assignment_id);
		GradeDTO gradeDTO = gradeService.getGradeById(grade_id);
		try {
			if(gradeDTO.getTimesSumbitted() < 3) {
				gradeDTO.setGrade(grade);
				gradeDTO.setAssignment(assignment);
				gradeDTO.setStudent(student);
				gradeDTO.setTimesSumbitted(gradeDTO.getTimesSumbitted()+1);
				return gradeService.updateGrade(grade_id, gradeDTO);
			}else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@DeleteMapping()
	public String deleteGrade(@RequestParam("id") Long grade_id) {
		try {
			gradeService.deleteGradeById(grade_id);
			return "Grade at id = " + grade_id + " was deleted succesfully";
		}catch(Exception e) {
			e.printStackTrace();
			return "Grade at id = " + " could not be deleted";
		}
	}
}
