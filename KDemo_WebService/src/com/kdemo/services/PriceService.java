package com.kdemo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kdemo.model.Price;
import com.kdemo.repositories.PriceRepositıry;

@Service
public class PriceService {
	
	@Autowired
	PriceRepositıry priceRepositıry;
	
	public Price create() {
		Price price = new Price();
		return price;
	}
	
	public Price findById (Long id) {
		return priceRepositıry.findOne(id);
	}
	
	public void delete (Price price) {
		priceRepositıry.delete(price);
	}
	
	public void add(Price price) {
		priceRepositıry.saveAndFlush(price);
	}
	
	public List<Price> findAll() {
		return priceRepositıry.findAll();
	}
	
	public Price findByType (String type) {
		return priceRepositıry.findByType(type);
	}

}
