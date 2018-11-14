package com.kdemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Columns;

@Entity
@Table(name="beanrequest")
public class BeanRequest {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column
	String nereden;
	
	@Column
	String nereye;
	
	@Column
	String tarih;
	
	@Column
	String distance;
	
	@Column
	String gondericiTipi;
	
	@Column
	boolean status;
	
	public BeanRequest() {
		
	}
	
	
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getNereden() {
		return nereden;
	}



	public void setNereden(String nereden) {
		this.nereden = nereden;
	}



	public String getNereye() {
		return nereye;
	}



	public void setNereye(String nereye) {
		this.nereye = nereye;
	}



	public String getTarih() {
		return tarih;
	}



	public void setTarih(String tarih) {
		this.tarih = tarih;
	}



	public String getDistance() {
		return distance;
	}



	public void setDistance(String distance) {
		this.distance = distance;
	}



	public String getGondericiTipi() {
		return gondericiTipi;
	}



	public void setGondericiTipi(String gondericiTipi) {
		this.gondericiTipi = gondericiTipi;
	}



	public boolean isStatus() {
		return status;
	}



	public void setStatus(boolean status) {
		this.status = status;
	}



	@Override
	public String toString() {
		return nereden + "," + nereye + "," + tarih + "," + distance
				+ "," + gondericiTipi;
	}	
}