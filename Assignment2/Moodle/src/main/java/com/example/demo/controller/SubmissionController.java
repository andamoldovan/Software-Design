package com.example.demo.controller;

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
import com.example.demo.service.StudentDTO;
import com.example.demo.service.StudentService;
import com.example.demo.service.SubmissionDTO;
import com.example.demo.service.SubmissionService;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/submissions")
public class SubmissionController {
	
	public final SubmissionService submissionService;
	public final StudentService studentService;
	public final AssignmentService assignmentService;
	
	@Autowired
	public SubmissionController(SubmissionService submissionService, StudentService studentService, AssignmentService assignmentService) {
		this.submissionService = submissionService;
		this.studentService = studentService;
		this.assignmentService = assignmentService;
	}
	
	
	@GetMapping(value = "/{id}")
	@ResponseBody
	public SubmissionDTO getSubmissionById(@RequestParam("id") Long id) {
		try {
			return submissionService.getSubmissionById(id);
		}catch(Exception e) {
			e.printStackTrace();
			//return "Could not find submission at id = " + id;
			return null;
		}
	}
	
	@PostMapping()
	public SubmissionDTO saveSubmission(@RequestParam("studentid") Long studnet_id, @RequestParam("assignmentid") Long assignment_id, @RequestParam("gitrepository") String git_repo, @RequestParam("note") String note) {
		StudentDTO student = studentService.getStudentById(studnet_id);
		student.setId(studnet_id);
		AssignmentDTO assignment = assignmentService.getAssignmentById(assignment_id);
		assignment.setId(assignment_id);
		SubmissionDTO sub = new SubmissionDTO(student, assignment, git_repo, note);
		
		try {
			return submissionService.saveSubmission(sub);
		
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PutMapping()
	public SubmissionDTO updateSubmission(@RequestParam("id") Long id, @RequestParam("studentid") Long studnet_id, @RequestParam("assignmentid") Long assignment_id, @RequestParam("geirepository") String git_repo, @RequestParam("note") String note) {
		StudentDTO student = studentService.getStudentById(studnet_id);
		student.setId(studnet_id);
		AssignmentDTO assignment = assignmentService.getAssignmentById(assignment_id);
		assignment.setId(assignment_id);
		SubmissionDTO sub = new SubmissionDTO(student, assignment, git_repo, note);
		try {
			return submissionService.updateAssignment(id, sub);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@DeleteMapping()
	public String deleteSubmission(@RequestParam("id") Long id) {
		try {
			submissionService.deleteById(id);
			return "Submission at id = " + id + " was deleted";
		}catch(Exception e) {
			e.printStackTrace();
			return "Submission at id = " + id + " coul not be deleted";
		}
	}
}
