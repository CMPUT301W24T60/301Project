package com.example.myapplication;

public class User {
    private String name;
    private String account;
    private String password;

    private String tickle;

    public User(){};

    public User(String name, String account, String password,String tickle) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.tickle = tickle;
    }

    public String getName() {
        return name;
    }
    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getTickle() {
        return tickle;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTickle(String tickle) {
        this.tickle = tickle;
    }
}
