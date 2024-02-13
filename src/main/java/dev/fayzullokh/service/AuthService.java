package dev.fayzullokh.service;

import dev.fayzullokh.configuration.security.JwtUtils;
import dev.fayzullokh.dtos.auth.RefreshTokenRequest;
import dev.fayzullokh.dtos.auth.TokenRequest;
import dev.fayzullokh.dtos.auth.TokenResponse;
import dev.fayzullokh.entity.User;
import dev.fayzullokh.enums.TokenType;
import dev.fayzullokh.exceptions.NotFoundException;
import dev.fayzullokh.repositories.UserRepository;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository authUserRepository;
    private final JwtUtils jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    @Timed(value = "auth_service_generate_token_time", description = "Time taken to generate token")
    @Counted(value = "auth_service_generate_token_count", description = "Number of times generateToken method is called")
    public TokenResponse generateToken(@NonNull TokenRequest tokenRequest) throws NotFoundException {
        String username = tokenRequest.username();
        String password = tokenRequest.password();

        log.debug("Generating token for user: {}", username);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        User byUsername = authUserRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Username '%s' not found".formatted(username)));

        if (Objects.isNull(byUsername)) {
            log.error("Username '{}' not found", username);
            throw new UsernameNotFoundException("Username '%s' not found".formatted(username));
        }

        if (password.equals(byUsername.getPassword())) {
            log.debug("User '{}' authenticated successfully", username);
            return jwtTokenUtil.generateToken(username);
        } else {
            log.error("Bad credentials for user '{}'", username);
            throw new BadCredentialsException("Bad credentials");
        }
    }
// for fun
    @Timed(value = "auth_service_refresh_token_time", description = "Time taken to refresh token")
    public TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.refreshToken();

        log.debug("Refreshing token");

        if (!jwtTokenUtil.isValid(refreshToken, TokenType.REFRESH)) {
            log.error("Invalid refresh token");
            throw new CredentialsExpiredException("Token is invalid");
        }

        String username = jwtTokenUtil.getUsername(refreshToken, TokenType.REFRESH);
        authUserRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Username '%s' not found".formatted(username)));

        TokenResponse tokenResponse = TokenResponse.builder()
                .refreshToken(refreshToken)
                .refreshTokenExpiry(jwtTokenUtil.getExpiry(refreshToken, TokenType.REFRESH))
                .build();

        return jwtTokenUtil.generateAccessToken(username, tokenResponse);
    }

}
