package com.android;

public class Kitap {
	
	int id;
	String kitap_adi;
	int kutuphid;
	int sayfa_sayisi;
	String ozellik;
	String yazarlari;
	String isbn;
	int adet;
	int suanki_adet;
	String ekitap_path;
	public int getId() {
		return id;
	}
	
	
	public Kitap(int id, int kutuphid,String kitap_adi, int sayfa_sayisi, String ozellik, String yazarlari, String isbn, int adet,
			int suanki_adet, String ekitap_path) {
		super();
		this.id = id;
		this.kutuphid = kutuphid;
		this.kitap_adi = kitap_adi;
		this.sayfa_sayisi = sayfa_sayisi;
		this.ozellik = ozellik;
		this.yazarlari = yazarlari;
		this.isbn = isbn;
		this.adet = adet;
		this.suanki_adet = suanki_adet;
		this.ekitap_path = ekitap_path;
	}

 
	public int getKutuphid() {
		return kutuphid;
	}


	public void setKutuphid(int kutuphid) {
		this.kutuphid = kutuphid;
	}


	public void setId(int id) {
		this.id = id;
	}
	public String getKitap_adi() {
		return kitap_adi;
	}
	public void setKitap_adi(String kitap_adi) {
		this.kitap_adi = kitap_adi;
	}
	public int getSayfa_sayisi() {
		return sayfa_sayisi;
	}
	public void setSayfa_sayisi(int sayfa_sayisi) {
		this.sayfa_sayisi = sayfa_sayisi;
	}
	public String getOzellik() {
		return ozellik;
	}
	public void setOzellik(String ozellik) {
		this.ozellik = ozellik;
	}
	public String getYazarlari() {
		return yazarlari;
	}
	public void setYazarlari(String yazarlari) {
		this.yazarlari = yazarlari;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public int getAdet() {
		return adet;
	}
	public void setAdet(int adet) {
		this.adet = adet;
	}
	public int getSuanki_adet() {
		return suanki_adet;
	}
	public void setSuanki_adet(int suanki_adet) {
		this.suanki_adet = suanki_adet;
	}
	public String getEkitap_path() {
		return ekitap_path;
	}
	public void setEkitap_path(String ekitap_path) {
		this.ekitap_path = ekitap_path;
	}
	
	public String toString() {
		return  "&"+this.id+"," +this.kutuphid+","+this.kitap_adi+","+this.sayfa_sayisi+","+this.ozellik+
				","+this.yazarlari+","+this.isbn+","+this.adet+","+ this.suanki_adet+","+this.ekitap_path;
	}

}
