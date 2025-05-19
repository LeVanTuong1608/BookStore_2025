// package com.example.myapp1.security;

// import io.jsonwebtoken.*;
// import org.springframework.stereotype.Component;

// import java.util.Date;

// @Component
// public class JwtTokenProvider {

// private final String JWT_SECRET = "your_jwt_secret_key";
// private final long JWT_EXPIRATION = 86400000L; // 1 day in milliseconds

// // Tạo JWT token từ email người dùng
// public String generateToken(String email) {
// Date now = new Date();
// Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

// return Jwts.builder()
// .setSubject(email) // Thông tin người dùng (email)
// .setIssuedAt(now) // Thời gian cấp token
// .setExpiration(expiryDate) // Thời gian hết hạn
// .signWith(SignatureAlgorithm.HS512, JWT_SECRET) // Sử dụng HS512 algorithm để
// ký token
// .compact();
// }

// // Lấy email từ JWT token
// public String getUsernameFromToken(String token) {
// return Jwts.parserBuilder() // Cập nhật sử dụng parserBuilder thay vì parser
// (phương thức cũ)
// .setSigningKey(JWT_SECRET) // Xác nhận key
// .build()
// .parseClaimsJws(token)
// .getBody()
// .getSubject(); // Lấy subject (email)
// }

// // Kiểm tra tính hợp lệ của token
// public boolean validateToken(String token) {
// try {
// Jwts.parserBuilder() // Cập nhật sử dụng parserBuilder
// .setSigningKey(JWT_SECRET)
// .build()
// .parseClaimsJws(token); // Parse token để kiểm tra tính hợp lệ
// return true;
// } catch (ExpiredJwtException e) {
// // Nếu token hết hạn
// System.out.println("Token has expired");
// } catch (JwtException | IllegalArgumentException e) {
// // Nếu token không hợp lệ hoặc có lỗi khác
// System.out.println("Invalid JWT token");
// }
// return false;
// }

// // Lấy thời gian hết hạn của token
// public Date getExpirationDateFromToken(String token) {
// return Jwts.parserBuilder() // Cập nhật sử dụng parserBuilder
// .setSigningKey(JWT_SECRET)
// .build()
// .parseClaimsJws(token)
// .getBody()
// .getExpiration(); // Lấy ngày hết hạn từ token
// }
// }
