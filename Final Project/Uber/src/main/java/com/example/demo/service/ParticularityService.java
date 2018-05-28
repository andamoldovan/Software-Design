package com.example.demo.service;

import java.util.List;

public interface ParticularityService {
	
	List<ParticularityDTO> getAllParticularities();
	
	ParticularityDTO getParticularityById(Long id);
	
	ParticularityDTO createParticularity(ParticularityDTO particularityDTO);
	
	ParticularityDTO upadateParticularity(Long id, ParticularityDTO particularityDTO);
	
	void deleteParticularity(Long id);
}
