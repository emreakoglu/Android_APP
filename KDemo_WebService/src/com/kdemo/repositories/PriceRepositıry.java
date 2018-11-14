package com.kdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdemo.model.Price;

public interface PriceRepositıry extends JpaRepository<Price, Long> {
	
	public Price findByType(String type);

}
