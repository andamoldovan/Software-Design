package com.example.demo.controller;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import java.util.Set;

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

import com.example.demo.dao.model.Attendance;
import com.example.demo.service.AttendanceDTO;
import com.example.demo.service.AttendanceService;
import com.example.demo.service.LaboratoryDTO;
import com.example.demo.service.LaboratoryService;
import com.example.demo.service.StudentDTO;
import com.example.demo.service.StudentService;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/attendances")
public class AttendanceController {
	
	public final AttendanceService attendanceService;
	
	@Autowired 
	public AttendanceController(AttendanceService attendanceService) {
		this.attendanceService = attendanceService;
	}
	
	
	@GetMapping(value = "/{id}")
	@ResponseBody
	public String getAttendanceById(@RequestParam("id") Long id) {
		try {
			return attendanceService.getById(id).toString();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{studentid}")
	@ResponseBody
	public String getAttendanceByStudent(@RequestParam("studentId") Long student_id){
		try {
			return attendanceService.getAttendancesByStudent(student_id).toString();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping(value = "/{laboratoryid}")
	@ResponseBody
	public String getAttendanceByLaboratory(@RequestParam("studentId") Long laboratory_id){
		try {
			return attendanceService.getAttendanceByLaboratory(laboratory_id).toString();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping()
	public String saveAttendance(@RequestParam("studentId") Long studentId, @RequestParam("laboratoryId") Long laboratoryId) {
		try {
			attendanceService.saveAttendance(studentId, laboratoryId);
			return "Laboratory attendance for student " + studentId + " was created succesfully";
		}catch(Exception e) {
			e.printStackTrace();
			return "Laboratory attendance for student " + studentId + " was not created";
		}
	}
	
	@PutMapping()
	public String updateAttendance(@RequestParam("id") Long id, @RequestParam("studentId") Long studentId, @RequestParam("laboratoryId") Long laboratoryId) {
		try {
			attendanceService.updateAttendance(id, studentId, laboratoryId);
			return "Laboratory attendance for student " + studentId + " was updated succesfully";
		}catch(Exception e) {
			e.printStackTrace();
			return "Laboratory attendance for student " + studentId + " was not updated";
		}
	}
	
	@DeleteMapping()
	public String deleteAttendance(@RequestParam("id") Long id) {
		try {
			attendanceService.deleteAttendanceById(id);
			return "Attendance at id " + id + "was deleted";
		}catch(Exception e) {
			e.printStackTrace();
			return "Attendance at id " + id + "was deleted";
		}
	}
}
