package com.example.demo.dao;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.model.Laboratory;

@Repository
@Transactional
public interface LaboratoryDAO extends JpaRepository<Laboratory, Long>{
	
	Laboratory findByDate(Date date);
	
	Laboratory findByNumber(int number);
}
