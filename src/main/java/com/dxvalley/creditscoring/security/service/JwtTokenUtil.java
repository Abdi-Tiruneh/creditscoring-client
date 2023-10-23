package com.dxvalley.creditscoring.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class JwtTokenUtil {
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000 * 2; // two week
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 30L * 24L * 60L * 60L * 1000L; // 1 month
    public static String SECRET_KEY;
    private static CustomUserDetailsService customUserDetailsService;

    public JwtTokenUtil(@Value("${SECRET_KEY}") String privateKey, CustomUserDetailsService customUserDetailsService) {
        JwtTokenUtil.SECRET_KEY = privateKey;
        JwtTokenUtil.customUserDetailsService = customUserDetailsService;
    }

    public static String generateAccessToken(UserDetails user, HttpServletRequest request) {
        customUserDetailsService.updateLastLogin(user.getUsername());

        List<String> role = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME)) // 30 minutes
                .withClaim("role", role)
                .sign(Algorithm.HMAC256(SECRET_KEY.getBytes()));
    }

    public static String generateRefreshToken(UserDetails user, HttpServletRequest request) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .withIssuer(request.getRequestURL().toString())
                .sign(Algorithm.HMAC256(SECRET_KEY.getBytes()));
    }

    public static String getSecretKey() {
        return SECRET_KEY;
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}

