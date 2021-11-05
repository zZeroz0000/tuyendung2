package com.example.tuyendung;

import java.io.Serializable;

public class User implements Serializable {
    public String email,name;

    public User(){}

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

}
