package com.example.demo.controller;

import java.sql.Date;
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

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/assignments")
public class AssignmentController {
	
	public final AssignmentService assignmentService;
	
	@Autowired
	public AssignmentController(AssignmentService assignmentService) {
		this.assignmentService = assignmentService;
	}
	
	
	@GetMapping(value = "/{id}")
	@ResponseBody
	public String getAssignmentById(@RequestParam("id") Long id) {
		try {
			return assignmentService.getAssignmentById(id).toString();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{laboratoryid}")
	@ResponseBody
	public String getAssignmentsByLaboratoryId(@RequestParam("laboratoryId") Long laboratory_id) {
		try {
			return (assignmentService.getAssignmentsForLaboratory(laboratory_id).toString());
		}catch(Exception e) {
			e.printStackTrace();
			return "Laboratory at id = " + laboratory_id + " does not have any assignments";
		}
	}
	
	@PostMapping()
	public String createAssignment(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("date") Date date, @RequestParam("laboratoryId") Long laboratory_id) {
		AssignmentDTO a = new AssignmentDTO(name, description, date);
		try {
			assignmentService.saveAssignment(a, laboratory_id);
			return "Assignment was saved succesfully";
		}catch(Exception e) {
			e.printStackTrace();
			return "Assignment was not saved succesfully";
		}
	}
	
	@PutMapping()
	public String updateAssignment(@RequestParam("id") Long id, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("date") Date date, @RequestParam("laboratoryId") Long laboratory_id) {
		try {
			AssignmentDTO assignmentDTO = new AssignmentDTO(name, description, date);
			assignmentService.updateAssignment(id, laboratory_id, assignmentDTO);
			return "Assignment at id = " + id + " was updated succesfully";
		}catch(Exception e) {
			e.printStackTrace();
			return "Assignment at id = " + id + " could not be updated";
		}
	}
	
	@DeleteMapping()
	public String deleteAssignment(@RequestParam("id") Long id) {
		try {
			assignmentService.deleteById(id);
			return "Assignment at id = " + id + " was deleted succesfully";
		}catch(Exception e) {
			e.printStackTrace();
			return "Assignment at id = " + id + " could not be deleted";
		}
	}
}
