package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.model.Attendance;
import com.example.demo.dao.model.Laboratory;
import com.example.demo.dao.model.Student;
import com.example.demo.service.LaboratoryDTO;
import com.example.demo.service.StudentDTO;

@Repository
@Transactional
public interface AttendanceDAO extends JpaRepository<Attendance, Long>{
	
	List<Attendance> findAttendanceByStudentReff(Student studentReff);
	
	List<Attendance> findAttendanceByLaboratoryReff(Laboratory laboratoryReff);
}
