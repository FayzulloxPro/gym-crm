package dev.fayzullokh.configuration.security;

import dev.fayzullokh.configuration.security.JwtUtils;
import dev.fayzullokh.dtos.auth.TokenResponse;
import dev.fayzullokh.enums.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilsTest {

    private JwtUtils jwtUtils;

    @Value("${jwt.access.token.secret.key}")
    private String secretKey;
    @Value("${jwt.access.token.expiry}")
    private long expiryInMinutes;

    @Value("${jwt.refresh.token.expiry}")
    private long refreshTokenExpiry;
    @Value("${jwt.refresh.token.secret.key}")
    public String REFRESH_TOKEN_SECRET_KEY;

    @BeforeEach
    public void setUp() {
        jwtUtils = new JwtUtils();
        jwtUtils.REFRESH_TOKEN_SECRET_KEY = REFRESH_TOKEN_SECRET_KEY;
        jwtUtils.setSecretKey(secretKey);
        jwtUtils.setExpiryInMinutes(expiryInMinutes);
        jwtUtils.setRefreshTokenExpiry(refreshTokenExpiry);
    }

    @Test
    public void testGenerateToken() {
        String username = "testuser";

        TokenResponse tokenResponse = jwtUtils.generateToken(username);

        assertNotNull(tokenResponse);
        assertNotNull(tokenResponse.getAccessToken());
        assertNotNull(tokenResponse.getRefreshToken());
    }

    @Test
    public void testGenerateAccessToken() {
        String username = "testuser";
        TokenResponse tokenResponse = new TokenResponse();

        jwtUtils.generateAccessToken(username, tokenResponse);

        assertNotNull(tokenResponse.getAccessToken());
    }

    @Test
    public void testGenerateRefreshToken() {
        String username = "testuser";
        TokenResponse tokenResponse = new TokenResponse();

        jwtUtils.generateRefreshToken(username, tokenResponse);

        assertNotNull(tokenResponse.getRefreshToken());
    }

    @Test
    public void testIsTokenValid() {
        String token = generateAccessToken("testuser");
        TokenType tokenType = TokenType.ACCESS;

        boolean isValid = jwtUtils.isTokenValid(token, tokenType);

        assertTrue(isValid);
    }

    @Test
    public void testIsTokenValidExpired() {
        String token = generateExpiredAccessToken("testuser");
        TokenType tokenType = TokenType.ACCESS;

        boolean isValid = jwtUtils.isTokenValid(token, tokenType);

        assertFalse(isValid);
    }

    @Test
    public void testGetUsername() {
        String token = generateAccessToken("testuser");
        TokenType tokenType = TokenType.ACCESS;

        String username = jwtUtils.getUsername(token, tokenType);

        assertEquals("testuser", username);
    }

    @Test
    public void testGetExpiry() {
        String token = generateAccessToken("testuser");
        TokenType tokenType = TokenType.ACCESS;

        Date expiry = jwtUtils.getExpiry(token, tokenType);

        assertNotNull(expiry);
    }

    private String generateAccessToken(String username) {
        Key key = Keys.hmacShaKeyFor(jwtUtils.getSecretKey().getBytes());
        return io.jsonwebtoken.Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .setIssuer("https://example.com")
                .signWith(key)
                .compact();
    }

    private String generateExpiredAccessToken(String username) {
        Key key = Keys.hmacShaKeyFor(jwtUtils.getSecretKey().getBytes());
        return io.jsonwebtoken.Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() - 1000))
                .setIssuer("https://example.com")
                .signWith(key)
                .compact();
    }
}
