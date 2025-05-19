package com.example.myapp1.dto;

public class UserDTO {
    private Long userid;
    private String email;
    private String password;
    private String fullname;
    private String address;
    private String phonenumber;
    private String role;

    public UserDTO() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public UserDTO(Long userid, String email, String password, String fullname,
            String address, String phonenumber,
            String role) {
        this.userid = userid;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.address = address;
        this.phonenumber = phonenumber;
        this.role = role;
    }

    private UserDTO(Builder builder) {
        this.userid = builder.userid;
        this.email = builder.email;
        this.password = builder.password;
        this.fullname = builder.fullname;
        this.address = builder.address;
        this.phonenumber = builder.phonenumber;
        this.role = builder.role;
    }

    public Long getUserid() {
        return userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pasword) {
        this.password = pasword;
    }

    public void setUserid(Long userid) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Builder class
    public static class Builder {
        private Long userid;
        private String email;
        private String password;
        private String fullname;
        private String address;
        private String phonenumber;
        private String role;

        public Builder userId(Long userid) {
            this.userid = userid;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder fullName(String fullname) {
            this.fullname = fullname;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder phonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(this);
        }
    }

}
