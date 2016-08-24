package com.akarakaya.ekutuphane;

/**
 * Created by akarakaya on 6.12.2015.
 */
public class User {
    int id;
    String adi;
    String soyadi;
    String tc;
    int yas;
    String meslek;
    String kullanici_adi;
    String sifre;
    String kisiye_ozel;
    int kutuphane_id;

    public User(int id, String adi, String soyadi, String tc, int yas, String meslek, String kullanici_adi,
                String sifre,String kisiye_ozel ,int kutuphane_id) {
        super();
        this.id = id;
        this.adi = adi;
        this.soyadi = soyadi;
        this.tc = tc;
        this.yas = yas;
        this.meslek = meslek;
        this.kullanici_adi = kullanici_adi;
        this.sifre = sifre;
        this.kisiye_ozel = kisiye_ozel;
        this.kutuphane_id = kutuphane_id;
    }
    public User() {

    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAdi() {
        return adi;
    }
    public void setAdi(String adi) {
        this.adi = adi;
    }
    public String getSoyadi() {
        return soyadi;
    }
    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
    }
    public String getTc() {
        return tc;
    }
    public void setTc(String tc) {
        this.tc = tc;
    }
    public int getYas() {
        return yas;
    }
    public void setYas(int yas) {
        this.yas = yas;
    }
    public String getMeslek() {
        return meslek;
    }
    public void setMeslek(String meslek) {
        this.meslek = meslek;
    }
    public String getKullanici_adi() {
        return kullanici_adi;
    }
    public void setKullanici_adi(String kullanici_adi) {
        this.kullanici_adi = kullanici_adi;
    }
    public String getSifre() {
        return sifre;
    }
    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
    public int getKutuphane_id() {
        return kutuphane_id;
    }

    public String getKisiye_ozel() {
        return kisiye_ozel;
    }

    public void setKisiye_ozel(String kisiye_ozel) {
        this.kisiye_ozel = kisiye_ozel;
    }

    public void setKutuphane_id(int kutuphane_id) {
        this.kutuphane_id = kutuphane_id;
    }

    public String toString() {
        return this.id+","+this.adi+","+this.soyadi+","+this.tc+","+this.yas+","+this.meslek+","+this.kullanici_adi+","+this.sifre+","+this.kisiye_ozel+","+this.kutuphane_id;
    }

}
