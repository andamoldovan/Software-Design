package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Particularity;

@Repository
@Transactional
public interface ParticularityDAO extends JpaRepository<Particularity, Long>{

}
