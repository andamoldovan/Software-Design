package com.example.demo.service;

import java.sql.Date;
import java.util.List;

import com.example.demo.dao.model.Laboratory;

public interface LaboratoryService {
	
	List<LaboratoryDTO> getAllLaboratories();
	
	List<LaboratoryDTO> getLaboratoryByKeyWord(String word);
	
	LaboratoryDTO getById(Long id);
	
	LaboratoryDTO getLaboratoryByNumber(int number);
	
	LaboratoryDTO getLaboratoryByDate(Date date);
	
	Laboratory saveLaboratory(LaboratoryDTO labDTO);
	
	Laboratory updateLaboratory(Long id, LaboratoryDTO labDTO);
	
	void deleteLaboratoryById(Long id);
}
