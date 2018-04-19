package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.model.Teacher;

@Repository
@Transactional
public interface TeacherDAO extends JpaRepository<Teacher, Long>{
	
	Teacher findByEmailAndPassword(String email, String password);
}
