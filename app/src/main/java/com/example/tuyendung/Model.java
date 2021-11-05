package com.example.tuyendung;

import java.io.Serializable;

public class Model implements Serializable {
    private int id;
    private byte[] proavatar;
    private String  name;
    private String description;

    //constructor

    public Model(int id, byte[] proavatar, String name,String description) {
        this.id = id;
        this.proavatar = proavatar;
        this.name = name;
        this.description=description;
    }
    //getter and setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getProavatar() {
        return proavatar;
    }

    public void setProavatar(byte[] proavatar) {
        this.proavatar = proavatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
