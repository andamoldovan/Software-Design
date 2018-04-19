package com.example.demo.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AssignmentDAO;
import com.example.demo.dao.LaboratoryDAO;
import com.example.demo.dao.model.Assignment;
import com.example.demo.dao.model.Laboratory;
import com.example.demo.service.AssignmentDTO;
import com.example.demo.service.AssignmentService;
import com.example.demo.service.LaboratoryDTO;

@Service
public class AssignmentServiceImpl implements AssignmentService{
	
	public final AssignmentDAO assignmentDAO;
	public final LaboratoryDAO laboratoryDAO;
	
	@Autowired
	public AssignmentServiceImpl(AssignmentDAO assignmentDAO, LaboratoryDAO laboratoryDAO) {
		this.assignmentDAO = assignmentDAO;
		this.laboratoryDAO = laboratoryDAO;
	}

	@Override
	public List<AssignmentDTO> getAllAssignments() {
		try {
			List<Assignment> list = assignmentDAO.findAll();
			return transform(list);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<AssignmentDTO> getAssignmentsForLaboratory(Long id) {
		try {
			Laboratory lab = laboratoryDAO.getOne(id);
			return transform(assignmentDAO.getAssignmentsByLaboratoryIdentifier(lab));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public AssignmentDTO getAssignmentById(Long id) {
		try {
			Assignment assignment = assignmentDAO.getOne(id);
			LaboratoryDTO l = new LaboratoryDTO(assignment.getLaboratoryIdentifier().getNumber(), assignment.getLaboratoryIdentifier().getDate(), 
									assignment.getLaboratoryIdentifier().getTitle(), assignment.getLaboratoryIdentifier().getCurricula(), assignment.getLaboratoryIdentifier().getDescription());
			l.setId(assignment.getLaboratoryIdentifier().getId());
			AssignmentDTO a = new AssignmentDTO(assignment.getName(), assignment.getDescription(), assignment.getDate());
			a.setId(assignment.getId());
			a.setLaboratoryDTO(l);
			return a;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Assignment saveAssignment(AssignmentDTO assignmentDTO, Long lab_id) {
		try {
			Laboratory laboratory = laboratoryDAO.getOne(lab_id);
			//Laboratory laboratory  = new Laboratory(labDTO.getNumber(), labDTO.getDate(), labDTO.getTitle(), labDTO.getCurricula(), labDTO.getDescription());
			Assignment a = new Assignment(assignmentDTO.getName(), assignmentDTO.getDescription(), assignmentDTO.getDate(), laboratory);
			return assignmentDAO.save(a);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Assignment updateAssignment(Long id, Long laboraotry_id, AssignmentDTO assignmentDTO) {
		Assignment a = new Assignment();
		Laboratory l = new Laboratory();
		try {
			a = assignmentDAO.getOne(id);
			l = laboratoryDAO.getOne(laboraotry_id);
			l.setId(laboraotry_id);
			a.setId(assignmentDTO.getId());
			a.setName(assignmentDTO.getName());
			a.setDescription(assignmentDTO.getDescription());
			a.setDate(assignmentDTO.getDate());
			a.setLaboratoryIdentifier(l);
			
			return a;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteById(Long id) {
		try {
			assignmentDAO.deleteById(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<AssignmentDTO> transform(List<Assignment> list){
		List<AssignmentDTO> result = new ArrayList<AssignmentDTO>();
		AssignmentDTO assignment;
		Laboratory l;
		LaboratoryDTO lab;
		for(Assignment a : list) {
			l = a.getLaboratoryIdentifier();
			l.setId(a.getLaboratoryIdentifier().getId());
			lab = new LaboratoryDTO(l.getNumber(), l.getDate(), l.getTitle(), l.getCurricula(), l.getDescription());
			lab.setId(l.getId());
			assignment = new AssignmentDTO();
			assignment.setId(a.getId());
			assignment.setDate(a.getDate());
			assignment.setDescription(a.getDescription());
			assignment.setName(a.getName());
			assignment.setLaboratoryDTO(lab);
			result.add(assignment);
		}
		return result;
	}

	
}
