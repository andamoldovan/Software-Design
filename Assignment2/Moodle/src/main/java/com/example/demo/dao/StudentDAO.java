package com.example.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.model.Student;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
@Transactional
public interface StudentDAO extends JpaRepository<Student, Long>{
	 
	Student findByEmail(String email);
	
	Student findByTokenAndEmail(String token, String email);
	
	Student findByEmailAndPassword(String email, String password);

}
