package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.TeacherDAO;
import com.example.demo.dao.model.Student;
import com.example.demo.dao.model.Teacher;
import com.example.demo.service.StudentDTO;
import com.example.demo.service.TeacherDTO;
import com.example.demo.service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService{
	
	private final TeacherDAO teacherDAO;
	
	@Autowired
	public TeacherServiceImpl(TeacherDAO teacherDAO) {
		this.teacherDAO = teacherDAO;
	}

	@Override
	public List<TeacherDTO> getAllTeachers() {
		try {
			return transform(teacherDAO.findAll());
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public TeacherDTO getTeacherById(Long id) {
		try {
			return transform(teacherDAO.getOne(id));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	@Override
	public TeacherDTO getTeacherByEmailAndPassword(String email, String password) {
		TeacherDTO test = new TeacherDTO(email, password);
		try {
			Teacher t = teacherDAO.findByEmailAndPassword(test.getEmail(), test.getPassword());
			TeacherDTO teacher = new TeacherDTO(t.getEmail(), password);
			teacher.setId(t.getId());
			return teacher;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public TeacherDTO saveTeacher(TeacherDTO teacherDTO) {
		Teacher saveTeacher = new Teacher(teacherDTO.getEmail(), teacherDTO.getPassword());
		try {
			return transform(teacherDAO.save(saveTeacher));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO) {
		Teacher teacher = teacherDAO.getOne(id);
		teacher.setEmail(teacherDTO.getEmail());
		teacher.setPassword(teacherDTO.getPassword());
		
		try {
			return transform(teacherDAO.save(teacher));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteTeacher(Long id) {
		teacherDAO.deleteById(id);
	}
	
	private List<TeacherDTO> transform(List<Teacher> list){
		List<TeacherDTO> result = new ArrayList<TeacherDTO>();
		TeacherDTO teacher;
		
		for(Teacher t : list) {
			teacher = new TeacherDTO(t.getEmail(), t.getPassword());
			teacher.setId(t.getId());
			result.add(teacher);
		}
		return result;
	}
	
	private TeacherDTO transform(Teacher teacher) {
		TeacherDTO teach = new TeacherDTO(teacher.getEmail(), teacher.getPassword());
		teach.setId(teacher.getId());
		
		return teach;
	}

}
