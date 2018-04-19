package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.dao.model.Student;

public interface StudentService {
	
	List<StudentDTO> getAllStudents();
	
	StudentDTO getStudentById(Long id);
	
	StudentDTO getStudentByEmail(String email);
	
	StudentDTO getStudentByEmailAndPassword(String email, String password);
	
	Student saveStudent(StudentDTO studentDTO);
	
	StudentDTO updateStudent(String email, StudentDTO studentDTO);
	
	void deleteStudent(Long id);
	
	Student firstLogIn(String email, String token, StudentDTO studentDTO);
	
}
