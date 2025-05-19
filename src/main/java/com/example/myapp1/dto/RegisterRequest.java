package com.example.myapp1.dto;

public class RegisterRequest {
    private String email;
    private String password;
    private String fullname;
    private String address;
    private String phonenumber;
    private String role; // Thêm role để admin có thể tạo user với role khác

    public RegisterRequest() {
    }

    // String address, String phonenumber,
    public RegisterRequest(String email, String password, String fullname, String address, String phonenumber,
            String role) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.phonenumber = phonenumber;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
