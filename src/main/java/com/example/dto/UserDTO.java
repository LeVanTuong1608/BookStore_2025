package com.example.dto;

public class UserDTO {
    private long userid;
    private String email;
    private String fullname;
    private String address;
    private String phonenumber;

    // Constructor, Getters, Setters
    public UserDTO() {
    }

    public UserDTO(long userid, String email, String fullname, String address, String phonenumber) {
        this.userid = userid;
        this.email = email;
        this.fullname = fullname;
        this.address = address;
        this.phonenumber = phonenumber;
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
}
