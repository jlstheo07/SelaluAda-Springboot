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
import java.util.List;

@Component
public class JwtFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    // Daftar endpoint publik yang tidak perlu token (bisa ditaruh di config terpisah juga)
    private static final List<String> PUBLIC_PATHS = List.of(
            "be/auth/login",
            "be/auth/forgot-password",
            "be/auth/reset-password",
            "be/api/users/register",
            "be/api/staff/register",
            "be/api/auth/signin",
            "be/api/auth/login-google"
    );

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

        // Lewatkan tanpa autentikasi jika path cocok dengan salah satu PUBLIC_PATHS
        for (String path : PUBLIC_PATHS) {
            if (requestURI.contains(path)) {
                chain.doFilter(request, response);
                return;
            }
        }

        // Validasi token
        if (authHeader == null || !authHeader.toLowerCase().startsWith("bearer ")) {
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
