package com.example.lhti;

import java.util.HashMap;
import java.util.Map;

public class Post {
    public String email;
    public double money;

    public String password;


    public Post() {
    }

    public Post(String email, double money,String password) {
        this.email = email;
        this.money = money;
        this.password=password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
