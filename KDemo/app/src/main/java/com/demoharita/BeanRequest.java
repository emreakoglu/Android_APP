package com.demoharita;

public class BeanRequest {
	
	String from;
	String to;
	String date;
	String distance;
	String gondericiTipi;
	
	public BeanRequest() {
		
	}
	public BeanRequest(String from, String to, String date, String distance, String gondericiTipi) {
		super();
		this.from = from;
		this.to = to;
		this.date = date;
		this.distance = distance;
		this.gondericiTipi = gondericiTipi;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	
	@Override
	public String toString() {
		return "BeanRequest [from=" + from + ", to=" + to + ", date=" + date + ", distance=" + distance
				+ ", gondericiTipi=" + gondericiTipi + "]";
	}	
}