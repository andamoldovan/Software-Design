package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ParticularityDTO;
import com.example.demo.service.ParticularityService;

@RestController
@RequestMapping("/particularities")
public class ParticularityController {
	
	private final ParticularityService particularityService;
	
	@Autowired
	public ParticularityController(ParticularityService particularityService) {
		this.particularityService = particularityService;
	}
	
	@GetMapping()
	public List<ParticularityDTO> getAllParitcularities(){
		try {
			return particularityService.getAllParticularities();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{id}")
	public ParticularityDTO getParticularityById(@RequestParam("id") Long id) {
		try {
			return particularityService.getParticularityById(id);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping()
	public ParticularityDTO createParitcularity(@RequestParam("spicy") int spicy, @RequestParam("gluten") int gluten, @RequestParam("lactose") int lactose, @RequestParam("hot") int hot, @RequestParam("nuts") int nuts, @RequestParam("vegan") int vegan) {
		try {
			ParticularityDTO particularityDTO = new ParticularityDTO(spicy, gluten, lactose, hot, nuts, vegan);
			return particularityService.createParticularity(particularityDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PutMapping()
	public ParticularityDTO updateParticularity(@RequestParam("id") Long id, @RequestParam("spicy") int spicy, @RequestParam("gluten") int gluten, @RequestParam("lactose") int lactose, @RequestParam("hot") int hot, @RequestParam("nuts") int nuts, @RequestParam("vegan") int vegan) {
		try {
			ParticularityDTO particularityDTO = new ParticularityDTO(spicy, gluten, lactose, hot, nuts, vegan);
			return particularityService.upadateParticularity(id, particularityDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@DeleteMapping()
	public String deleteParticularity(@RequestParam("id") Long id) {
		try {
			particularityService.deleteParticularity(id);
			return "Food particularity at id: " + id + " was deleted";
		}catch(Exception e) {
			e.printStackTrace();
			return "Food particularity at id: " + id + " could not be deleted"; 
		}
	}
}
