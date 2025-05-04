package com.example.myapp1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userid;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String full_name;

    @Column
    private String address;

    @Column
    private String phone_number;

    public User() {
    }

    public User(String email, String password, String full_name, String address, String phone_number) {
        this.email = email;
        this.password = password;
        this.full_name = full_name;
        this.address = address;
        this.phone_number = phone_number;
    }

    public User(long userid) {
        this.userid = userid;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return full_name;
    }

    public void setFullname(String full_name) {
        this.full_name = full_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phone_number;
    }

    public void setPhonenumber(String phone_number) {
        this.phone_number = phone_number;
    }
}
