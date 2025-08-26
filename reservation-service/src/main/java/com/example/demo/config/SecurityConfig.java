package com.example.demo.config;

import com.example.demo.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/**", "/health/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil);
    }

    public static class JwtAuthenticationFilter extends OncePerRequestFilter {
        
        private final JwtUtil jwtUtil;
        
        public JwtAuthenticationFilter(JwtUtil jwtUtil) {
            this.jwtUtil = jwtUtil;
        }
        
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            
            String authHeader = request.getHeader("Authorization");
            
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                
                try {
                    // Validate the JWT token
                    if (jwtUtil.validateToken(token) && !jwtUtil.isTokenExpired(token)) {
                        String username = jwtUtil.extractUsername(token);
                        if (username != null) {
                            // Create a simple authentication object
                            // In a real application, you might want to load user details from a database
                            org.springframework.security.authentication.UsernamePasswordAuthenticationToken authentication = 
                                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                                    username, null, java.util.Collections.emptyList());
                            
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            System.out.println("JWT authentication successful for user: " + username);
                        }
                    } else {
                        System.out.println("JWT validation failed: token invalid or expired");
                    }
                } catch (Exception e) {
                    System.out.println("JWT validation error: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("No Authorization header found");
            }
            
            filterChain.doFilter(request, response);
        }
    }
}
