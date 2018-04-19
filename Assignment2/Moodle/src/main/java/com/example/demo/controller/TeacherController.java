package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.TeacherDTO;
import com.example.demo.service.TeacherService;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/teachers")
public class TeacherController {
	
	public final TeacherService teacherService;
	
	@Autowired
	public TeacherController(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	
	
	@GetMapping(value="/{id}")
	@ResponseBody
	public TeacherDTO getTeacherById(@PathVariable("id") Long id) {
		try {
			return teacherService.getTeacherById(id);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping()
	@ResponseBody
	public TeacherDTO getTeacherByEmailAndPassword(@RequestParam("email") String email, @RequestParam("password") String password) {
		try {
			return teacherService.getTeacherByEmailAndPassword(email, password);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping()
	public TeacherDTO createTeacher(@RequestParam("email") String email, @RequestParam("password") String password) {
		TeacherDTO teacherDTO = new TeacherDTO(email, password);
		try {
			return teacherService.saveTeacher(teacherDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PutMapping()
	public TeacherDTO updateTeacher(@RequestParam("id") Long id, @RequestParam("email") String email, @RequestParam("password") String password) {
		TeacherDTO teacherDTO = new TeacherDTO(email, password);
		try {
			return teacherService.updateTeacher(id, teacherDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@DeleteMapping()
	public String deleteTeacher(@RequestParam("id") Long id) {
		try {
			teacherService.deleteTeacher(id);
			return "Teacher at id = " + id + " was deleted";
		}catch(Exception e) {
			e.printStackTrace();
			return "Teacher at id = " + " could not be deleted";
		}
	}
}
