package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.model.Assignment;
import com.example.demo.dao.model.Grade;
import com.example.demo.service.AssignmentDTO;
import com.example.demo.service.GradeDTO;

@Repository
@Transactional
public interface GradeDAO extends JpaRepository<Grade, Long>{
	
	List<Grade> findGradesByAssignmentIdentifier(Assignment assignment);
	
}
