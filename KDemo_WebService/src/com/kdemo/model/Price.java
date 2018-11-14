package com.kdemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="price")
public class Price {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column
	String type;
	
	@Column
	Long firstPrice;
	
	@Column
	Long increaseDistance;
	
	@Column
	Long increasePrice;
	
	public Price() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getFirstPrice() {
		return firstPrice;
	}

	public void setFirstPrice(Long firstPrice) {
		this.firstPrice = firstPrice;
	}

	public Long getIncreaseDistance() {
		return increaseDistance;
	}

	public void setIncreaseDistance(Long increaseDistance) {
		this.increaseDistance = increaseDistance;
	}

	public Long getIncreasePrice() {
		return increasePrice;
	}

	public void setIncreasePrice(Long increasePrice) {
		this.increasePrice = increasePrice;
	}
	
	
	
}
