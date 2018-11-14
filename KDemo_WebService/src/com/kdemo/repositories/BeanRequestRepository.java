package com.kdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kdemo.model.BeanRequest;

public interface BeanRequestRepository extends JpaRepository<BeanRequest, Long> {
	

}
