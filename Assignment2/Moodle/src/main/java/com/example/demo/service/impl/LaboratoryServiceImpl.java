package com.example.demo.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.LaboratoryDAO;
import com.example.demo.dao.model.Laboratory;
import com.example.demo.dao.model.Student;
import com.example.demo.service.LaboratoryDTO;
import com.example.demo.service.LaboratoryService;
import com.example.demo.service.StudentDTO;

@Service
public class LaboratoryServiceImpl implements LaboratoryService{

	private final LaboratoryDAO laboratoryDAO;
	
	@Autowired
	public LaboratoryServiceImpl(LaboratoryDAO laboratoryDAO) {
		this.laboratoryDAO = laboratoryDAO;
	}
	
	@Override
	public List<LaboratoryDTO> getAllLaboratories() {
		return transform(laboratoryDAO.findAll());
	}
	
	@Override
	public List<LaboratoryDTO> getLaboratoryByKeyWord(String word) {		
		List<LaboratoryDTO> list = transform(laboratoryDAO.findAll());
		List<LaboratoryDTO> result = new ArrayList<>();
				
		for(LaboratoryDTO l : list) {
			String[] description = l.getDescription().split(" ");
			String[] curricula = l.getCurricula().split(" ");
			
			for(String desc : description) {
				if(desc.equals(word)) result.add(l);
			}
			for(String curr : curricula) {
				if(curr.equals(word) && !result.contains(l)) result.add(l);
			}
		}
		
		return result;
	}

	@Override 
	public LaboratoryDTO getById(Long id) {
		Laboratory lab = new Laboratory();
		LaboratoryDTO labDTO;
		try {
			lab = laboratoryDAO.getOne(id);
			labDTO = new LaboratoryDTO(lab.getNumber(), lab.getDate(), lab.getTitle(), lab.getCurricula(), lab.getDescription());
			labDTO.setId(id);
			return labDTO;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public LaboratoryDTO getLaboratoryByNumber(int number) {
		Laboratory lab = laboratoryDAO.findByNumber(number);
		LaboratoryDTO labDTO = new LaboratoryDTO(lab.getNumber(), lab.getDate(), lab.getTitle(), lab.getCurricula(), lab.getDescription());
		labDTO.setId(lab.getId());
		return labDTO;
	}

	@Override
	public LaboratoryDTO getLaboratoryByDate(Date date) {
		Laboratory lab = laboratoryDAO.findByDate(date);
		LaboratoryDTO labDTO = new LaboratoryDTO(lab.getNumber(), lab.getDate(), lab.getTitle(), lab.getCurricula(), lab.getDescription());
		labDTO.setId(lab.getId());
		return labDTO;
	}

	@Override
	public LaboratoryDTO saveLaboratory(LaboratoryDTO labDTO) {
		Laboratory laboratory  = new Laboratory(labDTO.getNumber(), labDTO.getDate(), labDTO.getTitle(), labDTO.getCurricula(), labDTO.getDescription());
		
		try {
			return transform(laboratoryDAO.save(laboratory));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public LaboratoryDTO updateLaboratory(Long id, LaboratoryDTO labDTO) {
		Laboratory laboratory = laboratoryDAO.getOne(id);
		
		try {
			laboratory.setDate(labDTO.getDate());
			laboratory.setNumber(labDTO.getNumber());
			laboratory.setTitle(labDTO.getTitle());
			laboratory.setDescription(labDTO.getDescription());
			laboratory.setCurricula(labDTO.getCurricula());
			
			return transform(laboratoryDAO.save(laboratory));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteLaboratoryById(Long id) {
		laboratoryDAO.deleteById(id);
	}
	
	private List<LaboratoryDTO> transform(List<Laboratory> list){
		List<LaboratoryDTO> laboratories = new ArrayList<LaboratoryDTO>();
		LaboratoryDTO laboratoryDTO;
		for(Laboratory l : list) {
			laboratoryDTO = new LaboratoryDTO(l.getNumber(), l.getDate(), l.getTitle(), l.getCurricula(), l.getDescription());
			laboratoryDTO.setId(l.getId());
			laboratories.add(laboratoryDTO);
		}
		return laboratories;
	}
	
	
	private LaboratoryDTO transform(Laboratory l) {
		LaboratoryDTO laboratoryDTO =  new LaboratoryDTO(l.getNumber(), l.getDate(), l.getTitle(), l.getCurricula(), l.getDescription());
		laboratoryDTO.setId(l.getId());
		return laboratoryDTO;
	}
	
}
