package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.model.Assignment;
import com.example.demo.dao.model.Laboratory;

@Repository
@Transactional
public interface AssignmentDAO extends JpaRepository<Assignment, Long>{
	
	List<Assignment> getAssignmentsByLaboratoryIdentifier(Laboratory laboratoryIdentifier);
	
}
