package com.project;

public class User {
    
    private String username;
    private String password;
    private String email;

    public User(){
    }

    public User(String name, String pass, String mail){
        this.username = name;
        this.password = pass;
        this.email = mail;
    }
    
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
