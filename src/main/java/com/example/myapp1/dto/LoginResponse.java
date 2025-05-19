// package com.example.myapp1.dto;

// public class LoginResponse {
//     private String token;
//     private UserDTO user;

//     public LoginResponse() {
//     }

//     public LoginResponse(String token, UserDTO user) {
//         this.token = token;
//         this.user = user;
//     }

//     public String getToken() {
//         return token;
//     }

//     public void setToken(String token) {
//         this.token = token;
//     }

//     public UserDTO getUser() {
//         return user;
//     }

//     public void setUser(UserDTO user) {
//         this.user = user;
//     }
// }

package com.example.myapp1.dto;

public class LoginResponse {
    private String message;
    private UserDTO user;

    public LoginResponse(String message, UserDTO user) {
        this.message = message;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    // Getters & setters
}
