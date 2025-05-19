// // package com.example.myapp1.security;

// // import com.example.myapp1.model.User;
// // import com.example.myapp1.repository.UserRepository;
// // import io.jsonwebtoken.ExpiredJwtException;
// // import io.jsonwebtoken.JwtException;
// // import jakarta.servlet.FilterChain;
// // import jakarta.servlet.ServletException;
// // import jakarta.servlet.http.HttpServletRequest;
// // import jakarta.servlet.http.HttpServletResponse;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// // import org.springframework.security.core.context.SecurityContextHolder;
// // import org.springframework.stereotype.Component;
// // import org.springframework.util.StringUtils;
// // import org.springframework.web.filter.OncePerRequestFilter;

// // import java.io.IOException;
// // import java.util.Collections;

// // @Component
// // public class JwtAuthenticationFilter extends OncePerRequestFilter {

// //     @Autowired
// //     private JwtTokenProvider tokenProvider;

// //     @Autowired
// //     private UserRepository userRepository;

// //     @Override
// //     protected void doFilterInternal(HttpServletRequest request,
// //             HttpServletResponse response,
// //             FilterChain filterChain)
// //             throws ServletException, IOException {

// //         String token = getJwtFromRequest(request);

// //         if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
// //             try {
// //                 String email = tokenProvider.getUsernameFromToken(token);
// //                 User user = userRepository.findByEmail(email).orElse(null); // tìm theo email
// //                 if (user != null) {
// //                     UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
// //                             user, null, Collections.emptyList());
// //                     SecurityContextHolder.getContext().setAuthentication(authentication);
// //                 }
// //             } catch (ExpiredJwtException e) {
// //                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
// //                 response.getWriter().write("JWT token has expired");
// //                 return;
// //             } catch (JwtException | IllegalArgumentException e) {
// //                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
// //                 response.getWriter().write("Invalid JWT token");
// //                 return;
// //             }
// //         }

// //         filterChain.doFilter(request, response);
// //     }

// //     private String getJwtFromRequest(HttpServletRequest request) {
// //         String bearerToken = request.getHeader("Authorization");
// //         if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
// //             return bearerToken.substring(7);
// //         }
// //         return null;
// //     }
// // }

// package com.example.myapp1.security;

// import com.example.myapp1.model.User;
// import com.example.myapp1.repository.UserRepository;
// import io.jsonwebtoken.ExpiredJwtException;
// import io.jsonwebtoken.JwtException;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Component;
// import org.springframework.util.StringUtils;
// import org.springframework.web.filter.OncePerRequestFilter;

// import java.io.IOException;
// import java.util.Collections;

// @Component
// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     @Autowired
//     private JwtTokenProvider tokenProvider;

//     @Autowired
//     private UserRepository userRepository;

//     @Override
//     protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//         // Bỏ qua các route bắt đầu bằng /api/auth (login, register)
//         String path = request.getRequestURI();
//         return path.startsWith("/api/auth");
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request,
//             HttpServletResponse response,
//             FilterChain filterChain)
//             throws ServletException, IOException {

//         String token = getJwtFromRequest(request);

//         if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
//             try {
//                 String email = tokenProvider.getUsernameFromToken(token);
//                 User user = userRepository.findByEmail(email).orElse(null); // tìm theo email
//                 if (user != null) {
//                     UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                             user, null, Collections.emptyList());
//                     SecurityContextHolder.getContext().setAuthentication(authentication);
//                 }
//             } catch (ExpiredJwtException e) {
//                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                 response.getWriter().write("JWT token has expired");
//                 return;
//             } catch (JwtException | IllegalArgumentException e) {
//                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                 response.getWriter().write("Invalid JWT token");
//                 return;
//             }
//         }

//         filterChain.doFilter(request, response);
//     }

//     private String getJwtFromRequest(HttpServletRequest request) {
//         String bearerToken = request.getHeader("Authorization");
//         if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//             return bearerToken.substring(7); // Lấy phần token từ "Bearer <token>"
//         }
//         return null;
//     }
// }
