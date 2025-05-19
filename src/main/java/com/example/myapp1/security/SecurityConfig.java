// package com.example.myapp1.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpStatus;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// // import org.springframework.security.web.AuthenticationEntryPoint;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.access.AccessDeniedHandlerImpl;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.security.web.authentication.HttpStatusEntryPoint;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// import java.util.Arrays;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     private final JwtAuthenticationFilter jwtAuthenticationFilter;

//     public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
//         this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//     }

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//                 .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                 .csrf(csrf -> csrf.disable())
//                 .authorizeHttpRequests(auth -> auth
//                         .requestMatchers(
//                                 "/api/auth/**",
//                                 "/v3/api-docs/**",
//                                 "/swagger-ui/**",
//                                 "/frontend/**",
//                                 "/api/**",
//                                 "/frontend/admin/**", // ✅ Cho phép truy cập tất cả trong /frontend/
//                                 "/frontend/login.html",
//                                 "/css/**", "/js/**", "/images/**")

//                         .permitAll()
//                         .anyRequest().authenticated())
//                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 .exceptionHandling(exception -> exception
//                         .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                         .accessDeniedHandler(new AccessDeniedHandlerImpl()));

//         http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

//         return http.build();
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//         return config.getAuthenticationManager();
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public CorsConfigurationSource corsConfigurationSource() {
//         CorsConfiguration configuration = new CorsConfiguration();
//         configuration.setAllowedOrigins(Arrays.asList("*")); // Cho tất cả domain gọi
//         configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//         configuration.setAllowedHeaders(Arrays.asList("*"));
//         configuration.setExposedHeaders(Arrays.asList("Authorization"));

//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", configuration);
//         return source;
//     }
// }

package com.example.myapp1.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        // Cấu hình password encoder
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // Cấu hình CORS
        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                // configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));

                configuration.setAllowedOrigins(Arrays.asList("*")); // Cho phép mọi domain truy cập
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Các phương
                                                                                                           // thức cho
                                                                                                           // phép
                configuration.setAllowedHeaders(Arrays.asList("*")); // Cho phép mọi header
                configuration.setExposedHeaders(Arrays.asList("Authorization")); // Cho phép trả về header Authorization

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration); // Áp dụng cho tất cả đường dẫn
                return source;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable()) // Tắt CSRF
                                .authorizeHttpRequests(auth -> auth
                                                .anyRequest().permitAll() // Cho phép tất cả request
                                );

                return http.build();
        }
}
// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
// Exception {
// http
// .csrf(csrf ->
// csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
// .authorizeHttpRequests(auth -> auth
// .requestMatchers("/api/auth/register", "/api/auth/login",
// "/admin/**",
// "/css/**", "/js/**",
// "/frontend/login.html", "/frontend/admin/**",
// "/frontend/css/**", "/frontend/js/**")
// .permitAll()
// .requestMatchers("/admin/**").permitAll() // Bỏ qua xác thực cho trang
// // admin
// .anyRequest().authenticated())
// .formLogin(form -> form
// .loginPage("/frontend/login.html") // ✅ đường dẫn thực tế giao diện
// // login (file HTML)
// .loginProcessingUrl("/api/auth/login") // ✅ nơi submit POST form login
// .defaultSuccessUrl("/frontend/index.html", true) // ✅ chuyển hướng sau
// // khi login thành công
// .failureUrl("/frontend/login.html?error=true") // ✅ lỗi login
// .permitAll())

// .logout(logout -> logout
// .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
// .logoutSuccessUrl("/frontend/login.html?logout")
// .invalidateHttpSession(true)
// .deleteCookies("JSESSIONID")
// .permitAll())
// .sessionManagement(session -> session
// .invalidSessionUrl("/frontend/login.html?expired")
// .maximumSessions(1)
// .expiredUrl("/frontend/login.html?max_sessions"));
// .formLogin(form -> form
// .loginPage("/login")
// .loginProcessingUrl("/login")
// .defaultSuccessUrl("/", true)
// .failureUrl("/login?error")
// .permitAll())
// .logout(logout -> logout
// .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
// .logoutSuccessUrl("/login?logout")
// .invalidateHttpSession(true)
// .deleteCookies("JSESSIONID")
// .permitAll())
// .sessionManagement(session -> session
// .invalidSessionUrl("/login?expired")
// .maximumSessions(1)
// .expiredUrl("/login?max_sessions"));

// Cấu hình SecurityFilterChain
// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
// Exception {
// http
// .csrf(csrf ->
// csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
// .authorizeHttpRequests(auth -> auth
// .requestMatchers("/", "/api/auth/register", "/api/auth/login",
// "/frontend/css/**", "/frontend/js/**")
// .permitAll()
// .requestMatchers("/admin/**").hasRole("ADMIN")
// .requestMatchers("/user/**").hasRole("USER")
// .anyRequest().authenticated())
// .formLogin(form -> form
// .loginPage("/login")
// .loginProcessingUrl("/login")
// .defaultSuccessUrl("/", true)
// .failureUrl("/login?error")
// .permitAll())
// .logout(logout -> logout
// .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
// .logoutSuccessUrl("/login?logout")
// .invalidateHttpSession(true)
// .deleteCookies("JSESSIONID")
// .permitAll())
// .sessionManagement(session -> session
// .invalidSessionUrl("/login?expired")
// .maximumSessions(1)
// .expiredUrl("/login?max_sessions"));

// http
// .cors() // Kích hoạt CORS
// .and()
// .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) //
// Bảo mật
// // CSRF với
// // Cookie
// .authorizeRequests()
// .requestMatchers("/", "/api/auth/register", "/api/auth/login",
// "/frontend/css/**",
// "/frontend/js/**", "/images/**")
// .permitAll() // Cho phép truy cập không cần xác thực cho các endpoint này
// .requestMatchers("/admin/**").hasRole("ADMIN") // Yêu cầu quyền ADMIN để truy
// cập
// // /admin/**
// .requestMatchers("/user/**").hasRole("USER") // Yêu cầu quyền USER để truy
// cập /user/**
// .anyRequest().authenticated() // Yêu cầu xác thực cho tất cả các request còn
// lại
// .and()
// .formLogin()
// .loginPage("/login") // Trang đăng nhập tùy chỉnh
// .loginProcessingUrl("/login") // URL để form gửi dữ liệu đăng nhập
// .defaultSuccessUrl("/", true) // Chuyển hướng sau khi đăng nhập thành công
// .failureUrl("/login?error") // Chuyển hướng nếu đăng nhập thất bại
// .permitAll()
// .and()
// .logout()
// .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // URL logout
// .logoutSuccessUrl("/login?logout") // Chuyển hướng sau khi logout thành công
// .invalidateHttpSession(true) // Hủy session
// .deleteCookies("JSESSIONID") // Xóa cookie session
// .permitAll()
// .and()
// .sessionManagement()
// .invalidSessionUrl("/login?expired") // URL chuyển hướng nếu session hết hạn
// .maximumSessions(1) // Chỉ cho phép một session mỗi người dùng
// .expiredUrl("/login?max_sessions"); // Chuyển hướng nếu vượt quá số lượng
// session

// return http.build();
// }
// }
