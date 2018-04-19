package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.model.Submission;

@Repository
@Transactional
public interface SubmissionDAO extends JpaRepository<Submission, Long>{
	
}
