package dev.fayzullokh.dtos.auth;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TokenResponseTest {

    @Test
    void testAllArgsConstructor() {
        String accessToken = "sampleAccessToken";
        Date accessTokenExpiry = new Date();
        String refreshToken = "sampleRefreshToken";
        Date refreshTokenExpiry = new Date();

        TokenResponse tokenResponse = new TokenResponse(accessToken, accessTokenExpiry, refreshToken, refreshTokenExpiry);

        assertEquals(accessToken, tokenResponse.getAccessToken());
        assertEquals(accessTokenExpiry, tokenResponse.getAccessTokenExpiry());
        assertEquals(refreshToken, tokenResponse.getRefreshToken());
        assertEquals(refreshTokenExpiry, tokenResponse.getRefreshTokenExpiry());
    }

    @Test
    void testNoArgsConstructor() {
        TokenResponse tokenResponse = new TokenResponse();

        assertNull(tokenResponse.getAccessToken());
        assertNull(tokenResponse.getAccessTokenExpiry());
        assertNull(tokenResponse.getRefreshToken());
        assertNull(tokenResponse.getRefreshTokenExpiry());
    }

    @Test
    void testBuilder() {
        String accessToken = "sampleAccessToken";
        Date accessTokenExpiry = new Date();
        String refreshToken = "sampleRefreshToken";
        Date refreshTokenExpiry = new Date();

        TokenResponse tokenResponse = TokenResponse.builder()
                .accessToken(accessToken)
                .accessTokenExpiry(accessTokenExpiry)
                .refreshToken(refreshToken)
                .refreshTokenExpiry(refreshTokenExpiry)
                .build();

        assertEquals(accessToken, tokenResponse.getAccessToken());
        assertEquals(accessTokenExpiry, tokenResponse.getAccessTokenExpiry());
        assertEquals(refreshToken, tokenResponse.getRefreshToken());
        assertEquals(refreshTokenExpiry, tokenResponse.getRefreshTokenExpiry());
    }


}
