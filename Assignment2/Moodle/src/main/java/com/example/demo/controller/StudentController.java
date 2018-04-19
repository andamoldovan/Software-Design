package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.model.Student;
import com.example.demo.service.StudentDTO;
import com.example.demo.service.StudentService;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/students")
public class StudentController {
	
	private final StudentService studentService;
	
	@Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    
    @GetMapping(value = "/{id}")
    @ResponseBody
    public StudentDTO getStudentById(@RequestParam("id") Long id) {
    	try {
    		return studentService.getStudentById(id);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    @GetMapping(value = "/{email}")
    @ResponseBody
    public StudentDTO getStudentByEmail(@RequestParam("email") String email) {
    	try {
    		return studentService.getStudentByEmail(email);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    
    @GetMapping()
    @ResponseBody
    public StudentDTO getStudentByEmailAndPassword(@RequestParam("email") String email, @RequestParam("password") String password) {
    	try {
    		return studentService.getStudentByEmailAndPassword(email, password);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    @PostMapping()
    @ResponseBody
    public StudentDTO createStudent(@RequestParam("email") String email) {
    	StudentDTO s = studentService.getStudentByEmail(email);
    	try {
    		if(s == null) {
	    		StudentDTO student = new StudentDTO(email, 0, " ", " ", " ");
	    		student.setToken();
	    		if(student != null)
	    			return studentService.saveStudent(student);
	    		else return null;
    		}else
    			return null;
    	}catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    
    @PutMapping(value = "/{token}")
    @ResponseBody
    public StudentDTO firstLogIn(@RequestParam("email") String email, @RequestParam("token") String token, @RequestParam("fullName") String  fullName, @RequestParam("group") int group, @RequestParam("hobby") String hobby, @RequestParam("password") String password) {
    	StudentDTO student;
    	StudentDTO s = new StudentDTO(email, group, hobby, fullName, password);
    	try {
    		student = studentService.firstLogIn(email, token, s);
    		return student;
    	}catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    @PutMapping()
    @ResponseBody
    public StudentDTO updateStudent(@RequestParam("id") Long id, @RequestParam("email") String email, @RequestParam("fullName")  String fullName, @RequestParam("group") int group, @RequestParam("hobby") String hobby, @RequestParam("password") String password) {
    	StudentDTO student = studentService.getStudentById(id);
    	student.setEmail(email);
    	student.setFullName(fullName);
    	student.setGroup(group);
    	student.setHobby(hobby);
    	student.setPassword(password);
    	
    	try {
    		return studentService.updateStudent(email, student);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    @DeleteMapping()
    @ResponseBody
    public String deleteStudent(@RequestParam("id") Long id) {
    	try {
    		studentService.deleteStudent(id);
    		return "Student at id = " + id + " was deleted succesfully";
    	}catch(Exception e) {
    		e.printStackTrace();
    		return "Student at id = " + id + "could not be deleted";
    	}
    }
}
