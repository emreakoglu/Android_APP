package com.android;

public class Kutuphane {
	int id;
	String name;
	int il;
	
	public Kutuphane(int id, String name, int il) {
		super();
		this.id = id;
		this.name = name;
		this.il = il;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIl() {
		return il;
	}
	public void setIl(int il) {
		this.il = il;
	}
	
	public String toString() {
		return  "&"+this.id+"," +this.name+","+this.il;
	}

}
