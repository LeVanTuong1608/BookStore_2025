package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userid;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String fullname;

    private String address; // địa chỉ để gửi đồ

    private String phonenumber;

    public User() {

    }

    public User(String email, String password, String fullname, String address, String phonenumber) {
        this.address = address;
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.phonenumber = phonenumber;
    }

    // Getter và Setter cho các trường
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
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    // @Override
    // public String toString() {
    // return "User{" +
    // "userid=" + userid +
    // ", email='" + email + '\'' +
    // ", fullname='" + fullname + '\'' +
    // ", address='" + address + '\'' +
    // ", phonenumber='" + phonenumber + '\'' +
    // '}';
    // }
}
