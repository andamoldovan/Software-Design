package com.example.demo.service;

import java.util.List;

public interface TeacherService {

	List<TeacherDTO> getAllTeachers();
	
	TeacherDTO getTeacherById(Long id);
	
	TeacherDTO getTeacherByEmailAndPassword(String email, String password);
	
	TeacherDTO saveTeacher(TeacherDTO teacherDTO);
	
	TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO);
	
	void deleteTeacher(Long id);
}
