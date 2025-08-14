package com.spring.toogoodtogo.confing;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    //private final String SECRET_KEY = System.getenv("SECRET_KEY");
    private final String SECRET_KEY = "MySuperSecretKeyForJwtMySuperSecretKeyForJwt"; //최소 32자 이상
    private final long EXPIRATION = 1000L * 60 * 60; // 액세스 토큰 만료 시간 1시간

    private final Key key;

    public JwtUtil() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }


    /*
    * 비밀키를 바이트배열로 변환 후 서명용 Key 객체로 생성.
    * */
    public Key getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    //1. JWT 생성.
    public String generateToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) //토큰의 주체(보통 사용자 식별) 설정.
                .claim("UserId", userDetails.getUserId()) // 커스텀 클레임 설정(예: 사용자 권한)
                .claim("role",userDetails.getAuthorities())
                .setIssuedAt(Date.from(Instant.now())) //발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION)) // 만료 시간 설정
                .signWith(key,SignatureAlgorithm.HS256) // 비밀키로 서명
                .compact(); // 결과적으로 UserID + role을 포함하는 JWT 문자열을 생성한다.
    }

    //2. HTTP 요청에서 토큰 추출
    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader(AUTH_HEADER);

        if(header == null || !header.startsWith(BEARER_PREFIX)){
            return null;
        }
        return header.substring(BEARER_PREFIX.length());
    }
    //3. Claims 추출
    public Claims parseToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey()) // JWT 파서 객체 생성
                .build()
                .parseClaimsJws(token)
                .getBody();// getBody를 통해 Payload(Claims) 추출.
    }

    //4. Token에서 User 정보 추출
    public String getUserEmailFromToken(String token) {
        return parseToken(token).getSubject();
    }
    public Long getUserIdFromToken(String token) {
        return parseToken(token).get("userId", Long.class);
    }
    public String getRoleFromToken(String token) {
        return parseToken(token).get("role", String.class);
    }

    //5. 토큰 만료 여부 확인
    public boolean isTokenExpired(String token) {
        return parseToken(token).getExpiration().before(new Date());
    }

    //6. 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            parseToken(token); // 파싱/서명 검증
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
