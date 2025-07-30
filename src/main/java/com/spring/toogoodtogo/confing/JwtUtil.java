package com.spring.toogoodtogo.confing;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.*;

@Component
public class JwtUtil {

    //private final String SECRET_KEY = System.getenv("SECRET_KEY");
    private final String SECRET_KEY = "MySuperSecretKeyForJwtMySuperSecretKeyForJwt"; //최소 32자 이상
    private final long EXPIRATION = 1000L * 60 * 60 * 24; // 24시간

    public Key getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("UserId", userDetails.getUserId())
                .claim("role",userDetails.getAuthorities())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSecretKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
