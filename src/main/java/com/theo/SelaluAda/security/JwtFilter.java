package com.theo.SelaluAda.security;

import com.theo.SelaluAda.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class JwtFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String authHeader = httpRequest.getHeader("Authorization");
        String requestURI = httpRequest.getRequestURI();

        System.out.println("Incoming Request URI: " + requestURI);
        System.out.println("Raw Authorization Header: [" + authHeader + "]");

        // Endpoint yang tidak perlu autentikasi (sesuaikan dengan yang di SecurityConfig)
        // Hanya lewati endpoint yang memang tidak perlu otentikasi
        if (
                requestURI.startsWith("be/auth/login") ||
                        requestURI.startsWith("/auth/forgot-password") ||
                        requestURI.startsWith("/auth/reset-password") ||
                        requestURI.startsWith("/api/users/register") ||
                        requestURI.startsWith("/api/auth/login-google")
        ) {
            chain.doFilter(request, response);
            return;
        }


        // Validasi token
        if (authHeader == null || !authHeader.toLowerCase().startsWith("bearer ")) {

            System.out.println("Raw Authorization Header: [" + authHeader + "]");
            System.out.println("Authorization header missing or invalid.");
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.replaceFirst("(?i)^Bearer\\s+", "").trim();
        System.out.println("Processed Token: [" + token + "]");

        try {
            String email = jwtUtil.extractidUser(token);
            System.out.println("Extracted Email from Token: " + email);

            if (email == null || !jwtUtil.validateToken(token, email)) {
                System.out.println("Token validation failed.");
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
                return;
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("User authenticated: " + email);

        } catch (Exception e) {
            System.out.println("JWT Filter error: " + e.getMessage());
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token processing error");
            return;
        }

        chain.doFilter(request, response);
    }
}
