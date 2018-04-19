package com.example.demo.service;

import java.util.List;
import com.example.demo.dao.model.Attendance;

public interface AttendanceService {
	
	List<AttendanceDTO> getAllAttendances();
	
	AttendanceDTO getById(Long id);
	
	List<AttendanceDTO> getAttendancesByStudent(Long id);
	
	List<AttendanceDTO> getAttendanceByLaboratory(Long laboratory_id);
	
	Attendance saveAttendance(Long studentId, Long laboratoryId);
	
	Attendance updateAttendance(Long id, Long studentId, Long LaboratoryId);
	
	void deleteAttendanceById(Long id);
}
