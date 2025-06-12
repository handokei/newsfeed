package com.example.newsfeed.common.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct; // Spring Boot 3.x 이상
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    // application.properties에서 설정한 시크릿 키 값을 주입받습니다.
    @Value("${jwt.secret.key}")
    private String secretKeyString;

    // application.properties에서 설정한 토큰 만료 시간(ms)을 주입받습니다.
    @Value("${jwt.expiration.ms}")
    private long expirationMillis;

    // JWT 서명에 사용할 SecretKey 객체입니다.
    private SecretKey secretKey;

    // @PostConstruct: 의존성 주입이 완료된 후 실행되는 메서드입니다.
    // secretKeyString 값을 사용하여 SecretKey 객체를 초기화합니다.
    @PostConstruct
    public void init() {
        // 주입받은 secretKeyString을 바이트 배열로 변환하여 SecretKey를 생성합니다.
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 사용자 ID를 기반으로 JWT를 생성합니다.
     * @param userId 사용자 ID
     * @return 생성된 JWT 문자열
     */
    public String createJwt(Long userId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMillis); // 설정된 만료 시간 적용

        return Jwts.builder()
                .subject(userId.toString()) // 사용자 ID를 subject로 설정
                .issuedAt(now)              // 토큰 발행 시간
                .expiration(expiration)     // 토큰 만료 시간
                .claim("role", "admin") // 커스텀 클레임 예시
                .signWith(secretKey)        // 초기화된 SecretKey로 서명
                .compact();
    }

    /**
     * 토큰을 검증하고 페이로드(Claims)를 반환합니다.
     * 유효하지 않은 토큰(만료, 서명 불일치 등)의 경우 예외가 발생합니다.
     * @param token 검증할 JWT 토큰
     * @return 토큰의 페이로드(Claims)
     */
    private Claims verifyAndGetClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey) // 초기화된 SecretKey로 검증
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 토큰에서 사용자 ID를 추출합니다.
     * @param token JWT 토큰
     * @return 사용자 ID
     */
    public long getUserIdFromToken(String token) {
        Claims claims = verifyAndGetClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    /**
     * 토큰에서 특정 클레임 값을 추출합니다. (예: "role")
     * @param token JWT 토큰
     * @param claimName 추출할 클레임의 이름
     * @return 클레임 값 (Object 타입)
     */
    public Object getClaimFromToken(String token, String claimName) {
        Claims claims = verifyAndGetClaims(token);
        return claims.get(claimName);
    }
}