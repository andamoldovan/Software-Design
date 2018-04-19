package com.example.demo.controller;

import java.sql.Date;
import java.util.List;

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

import com.example.demo.dao.model.Laboratory;
import com.example.demo.service.LaboratoryDTO;
import com.example.demo.service.LaboratoryService;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/laboratories")
public class LaboratoryController {
	
	private final LaboratoryService laboratoryService;
	
	@Autowired
	public LaboratoryController(LaboratoryService laboratoryService) {
		this.laboratoryService = laboratoryService;
	}
	
	@GetMapping()
	@ResponseBody
	public String getAllLaboratories(){
		try {
			return laboratoryService.getAllLaboratories().toString();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{id}")
	@ResponseBody
	public String getLaboratoryById(@RequestParam("id") Long id) {
		try {
			return laboratoryService.getById(id).toString();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{number}")
	@ResponseBody
	public String getLaboratoyByNumber(@RequestParam("number") int number) {
		try {
			return laboratoryService.getLaboratoryByNumber(number).toString();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{date}")
	@ResponseBody
	public String getLaboratoyByDate(@RequestParam("date") Date date) {
		try {
			return laboratoryService.getLaboratoryByDate(date).toString();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{keyword}")
	@ResponseBody
	public String getLaboratoriesByKeyWord(@RequestParam("keyword") String word) {
		try {
			return (laboratoryService.getLaboratoryByKeyWord(word).toString());
		}catch(Exception e) {
			e.printStackTrace();
			return "Nothing found";
		}
	}
	
	@PostMapping()
	@ResponseBody
	public String createLaboratory(@RequestParam("number") int number, @RequestParam("date") Date date, @RequestParam("title") String title,  @RequestParam("curricula") String curricula,  @RequestParam("description") String description) {
		LaboratoryDTO laboratory = new LaboratoryDTO();
		
		laboratory.setNumber(number);
		laboratory.setDate(date);
		laboratory.setCurricula(curricula);
		laboratory.setDescription(description);
		laboratory.setTitle(title);
		try {
			laboratoryService.saveLaboratory(laboratory);
			return "Laboratory created succesfully";
		}catch(Exception e) {
			e.printStackTrace();
			return "Unable to save the laboratory";
		}
	}
	
	@PutMapping()
	public String updateLaboratory(@RequestParam("id") Long id, @RequestParam("number") int number, @RequestParam("date") Date date, @RequestParam("title") String title,  @RequestParam("curricula") String curricula,  @RequestParam("description") String description) { 
		LaboratoryDTO laboratory = laboratoryService.getById(id);
		
		laboratory.setNumber(number);
		laboratory.setDate(date);
		laboratory.setCurricula(curricula);
		laboratory.setDescription(description);
		laboratory.setTitle(title);
		
		try {
			laboratoryService.updateLaboratory(id, laboratory);
			return "Laboratory at id " + id + " was updated succesfully";
		}catch(Exception e) {
			return "Laboratory at id " + id + " was not updated succesfully";
		}
	}
	
	@DeleteMapping()
	public String deleteLaboratory(@RequestParam("id") Long id) {
		try {
			laboratoryService.deleteLaboratoryById(id);
			return "Laboratory at id " + id + " was deleted";
		}catch(Exception e) {
			e.printStackTrace();
			return "Laboratory at id " + id + " could not be deleted";
		}
	}
}
