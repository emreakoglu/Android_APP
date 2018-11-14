package kdemo.test;

public class JSPBeanRequest {
	
	int id;
	String from;
	String to;
	String date;
	String distance;
	String gondericiTipi;
	String status;
	
	public JSPBeanRequest(int id,String from, String to, String date, String distance, String gondericiTipi, String status) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.date = date;
		this.distance = distance;
		this.gondericiTipi = gondericiTipi;
		this.status = status;
	}
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return id + "&" + from + "&" + to + "&" + date + "&"
				+ distance + "&" + gondericiTipi + "&" + status;
	}
	
	
}
