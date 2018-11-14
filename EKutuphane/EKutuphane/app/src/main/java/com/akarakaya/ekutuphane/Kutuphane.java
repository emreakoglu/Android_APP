package com.akarakaya.ekutuphane;

/**
 * Created by akarakaya on 6.12.2015.
 */
public class Kutuphane {

    int id;
    String name;
    String il;

    public Kutuphane(int id, String name, String il) {
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
    public String getIl() {
        return il;
    }
    public void setIl(String il) {
        this.il = il;
    }

    public String toString() {
        return  this.getName();
    }
}
