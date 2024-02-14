package dev.fayzullokh.configuration.security;

import dev.fayzullokh.configuration.security.JwtUtils;
import dev.fayzullokh.dtos.auth.TokenResponse;
import dev.fayzullokh.enums.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilsTest {

    private JwtUtils jwtUtils;

    @BeforeEach
    public void setUp() {
        jwtUtils = new JwtUtils();
        jwtUtils.REFRESH_TOKEN_SECRET_KEY = "refresh_token_secret_key";
        jwtUtils.setSecretKey("access_token_secret_key");
        jwtUtils.setExpiryInMinutes(60); // 1 hour
        jwtUtils.setRefreshTokenExpiry(60 * 24 * 30);// 30 days
    }

    @Test
    public void testGenerateToken() {
        // Arrange
        String username = "testuser";

        // Act
        TokenResponse tokenResponse = jwtUtils.generateToken(username);

        // Assert
        assertNotNull(tokenResponse);
        assertNotNull(tokenResponse.getAccessToken());
        assertNotNull(tokenResponse.getRefreshToken());
    }

    @Test
    public void testGenerateAccessToken() {
        // Arrange
        String username = "testuser";
        TokenResponse tokenResponse = new TokenResponse();

        // Act
        jwtUtils.generateAccessToken(username, tokenResponse);

        // Assert
        assertNotNull(tokenResponse.getAccessToken());
    }

    @Test
    public void testGenerateRefreshToken() {
        // Arrange
        String username = "testuser";
        TokenResponse tokenResponse = new TokenResponse();

        // Act
        jwtUtils.generateRefreshToken(username, tokenResponse);

        // Assert
        assertNotNull(tokenResponse.getRefreshToken());
    }

    @Test
    public void testIsTokenValid() {
        // Arrange
        String token = generateAccessToken("testuser");
        TokenType tokenType = TokenType.ACCESS;

        // Act
        boolean isValid = jwtUtils.isTokenValid(token, tokenType);

        // Assert
        assertTrue(isValid);
    }

    @Test
    public void testIsTokenValidExpired() {
        // Arrange
        String token = generateExpiredAccessToken("testuser");
        TokenType tokenType = TokenType.ACCESS;

        // Act
        boolean isValid = jwtUtils.isTokenValid(token, tokenType);

        // Assert
        assertFalse(isValid);
    }

    @Test
    public void testGetUsername() {
        // Arrange
        String token = generateAccessToken("testuser");
        TokenType tokenType = TokenType.ACCESS;

        // Act
        String username = jwtUtils.getUsername(token, tokenType);

        // Assert
        assertEquals("testuser", username);
    }

    @Test
    public void testGetExpiry() {
        // Arrange
        String token = generateAccessToken("testuser");
        TokenType tokenType = TokenType.ACCESS;

        // Act
        Date expiry = jwtUtils.getExpiry(token, tokenType);

        // Assert
        assertNotNull(expiry);
    }

    private String generateAccessToken(String username) {
        Key key = Keys.hmacShaKeyFor(jwtUtils.getSecretKey().getBytes());
        return io.jsonwebtoken.Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .setIssuer("https://example.com")
                .signWith(key)
                .compact();
    }

    private String generateExpiredAccessToken(String username) {
        Key key = Keys.hmacShaKeyFor(jwtUtils.getSecretKey().getBytes());
        return io.jsonwebtoken.Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() - 1000)) // Expired token
                .setIssuer("https://example.com")
                .signWith(key)
                .compact();
    }
}
