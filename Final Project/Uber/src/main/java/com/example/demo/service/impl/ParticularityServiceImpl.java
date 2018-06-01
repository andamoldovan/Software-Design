package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ParticularityDAO;
import com.example.demo.model.Particularity;
import com.example.demo.service.ParticularityDTO;
import com.example.demo.service.ParticularityService;

@Service
public class ParticularityServiceImpl implements ParticularityService{
	
	private final ParticularityDAO particularityDAO;
	
	@Autowired
	public ParticularityServiceImpl(ParticularityDAO particularityDAO) {
		this.particularityDAO = particularityDAO;
	}
	
	@Override
	public List<ParticularityDTO> getAllParticularities() {
		try {
			return transform(particularityDAO.findAll());
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ParticularityDTO getParticularityById(Long id) {
		try {
			return transform(particularityDAO.getOne(id));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ParticularityDTO createParticularity(ParticularityDTO particularityDTO) {
		try {
			Particularity part = new Particularity(particularityDTO.getSpicy(), particularityDTO.getGluten(), particularityDTO.getLactose(), particularityDTO.getHot(), particularityDTO.getNuts(), particularityDTO.getVegan());
			return transform(particularityDAO.save(part));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ParticularityDTO upadateParticularity(Long id, ParticularityDTO particularityDTO) {
		try {
			Particularity part = particularityDAO.getOne(id);
			part.setSpicy(particularityDTO.getSpicy());
			part.setGluten(particularityDTO.getGluten());
			part.setLactose(particularityDTO.getLactose());
			part.setHot(particularityDTO.getHot());
			part.setNuts(particularityDTO.getNuts());
			part.setVegan(particularityDTO.getVegan());
			
			return transform(particularityDAO.save(part));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteParticularity(Long id) {
		particularityDAO.delete(particularityDAO.getOne(id));
	}
	
	private List<ParticularityDTO> transform(List<Particularity> list){
		List<ParticularityDTO> result = new ArrayList<ParticularityDTO>();
		ParticularityDTO part;
		
		for(Particularity p : list) {
			part = new ParticularityDTO(p.getSpicy(), p.getGluten(), p.getLactose(), p.getHot(), p.getNuts(), p.getVegan());
			part.setId(p.getId());
			result.add(part);
		}
		return result;
	}
	
	private ParticularityDTO transform(Particularity p) {
		ParticularityDTO part = new ParticularityDTO(p.getSpicy(), p.getGluten(), p.getLactose(), p.getHot(), p.getNuts(), p.getVegan());
		part.setId(p.getId());
		return part;
	}
	
}
