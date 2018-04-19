package com.example.demo.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AttendanceDAO;
import com.example.demo.dao.LaboratoryDAO;
import com.example.demo.dao.StudentDAO;
import com.example.demo.dao.model.Attendance;
import com.example.demo.dao.model.Laboratory;
import com.example.demo.dao.model.Student;
import com.example.demo.service.AttendanceDTO;
import com.example.demo.service.AttendanceService;
import com.example.demo.service.LaboratoryDTO;
import com.example.demo.service.StudentDTO;

@Service
public class AttendanceServiceImpl implements AttendanceService{

	public final AttendanceDAO attendanceDAO;
	public final StudentDAO studentDAO;
	public final LaboratoryDAO laboratoryDAO;
	
	@Autowired
	public AttendanceServiceImpl(AttendanceDAO attendanceDAO, StudentDAO studentDAO, LaboratoryDAO laboratoryDAO) {
		this.attendanceDAO = attendanceDAO;
		this.studentDAO = studentDAO;
		this.laboratoryDAO = laboratoryDAO;
	}
	
	@Override
	public List<AttendanceDTO> getAllAttendances() {
		
		try {
			List<Attendance> list = attendanceDAO.findAll();
			return transform(list);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public AttendanceDTO getById(Long id) {
		Attendance attendance;
		Student s;
		Laboratory l;
		try {
			attendance = attendanceDAO.getOne(id);
			s = attendance.getStudent();
			l = attendance.getLaboratory();
			return new AttendanceDTO(id, new StudentDTO(s.getEmail(), s.getGroup(), s.getHobby(), s.getFullName(), s.getPassword()), 
					new LaboratoryDTO(l.getNumber(), l.getDate(), l.getTitle(), l.getCurricula(), l.getDescription()));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<AttendanceDTO> getAttendancesByStudent(Long id) {
		List<Attendance> studentList = new ArrayList<Attendance>();
		try {
			Student s = studentDAO.getOne(id);
			studentList = attendanceDAO.findAttendanceByStudentReff(s);
			return transform(studentList);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<AttendanceDTO> getAttendanceByLaboratory(Long laboratory_id) {
		List<Attendance> laboratoryList = new ArrayList<Attendance>();
		try {
			Laboratory l = laboratoryDAO.getOne(laboratory_id);
			laboratoryList = attendanceDAO.findAttendanceByLaboratoryReff(l);
			return transform(laboratoryList);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Attendance saveAttendance(Long studentId, Long laboratoryId) {
		Student stu = studentDAO.getOne(studentId);
		Laboratory lab = laboratoryDAO.getOne(laboratoryId);
		
		stu.getStudentAttendance().add(lab);
		lab.getLaboratoryAttendances().add(stu);
		
		Attendance attendance = new Attendance(stu, lab);
		
		try {
			studentDAO.save(stu);
			laboratoryDAO.save(lab);
			return attendanceDAO.save(attendance);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Attendance updateAttendance(Long id, Long studentId, Long laboratoryId) {
		Student stu = studentDAO.getOne(studentId);
		Laboratory lab = laboratoryDAO.getOne(laboratoryId);
		Attendance att = attendanceDAO.getOne(id);
		att.setStudent(stu);
		att.setLaboratory(lab);
		
		
		try {
			return attendanceDAO.save(att);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteAttendanceById(Long id) {
		Attendance att = attendanceDAO.getOne(id);
		attendanceDAO.delete(att);
	}


	private List<AttendanceDTO> transform(List<Attendance> list){
		List<AttendanceDTO> attendance = new ArrayList<AttendanceDTO>();
		AttendanceDTO dto;
		Student s;
		StudentDTO student; 
		LaboratoryDTO lab;
		Laboratory l;
		for(Attendance a : list) {
			s = a.getStudent();
			l = a.getLaboratory();
			student = new StudentDTO(s.getEmail(), s.getGroup(), s.getHobby(), s.getFullName(), s.getPassword());
			student.setId(s.getStudentId());
			lab = new LaboratoryDTO(l.getNumber(), l.getDate(), l.getTitle(), l.getCurricula(), l.getDescription());
			lab.setId(l.getId());
			dto = new AttendanceDTO();
			dto.setStudentDTO(student);
			dto.setLaboratoryDTO(lab);
			dto.setId(a.getId());
			attendance.add(dto);
		}
		return attendance;
	}
	
}
