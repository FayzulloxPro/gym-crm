package dev.fayzullokh.service;

import dev.fayzullokh.service.BruteForceProtectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BruteForceProtectionServiceTest {

    private BruteForceProtectionService bruteForceProtectionService;

    @BeforeEach
    public void setUp() {
        bruteForceProtectionService = new BruteForceProtectionService();
    }

    @Test
    public void testLoginFailed() {
        // Arrange
        String username = "user1";

        // Act
        bruteForceProtectionService.loginFailed(username);

        // Assert
        assertFalse(bruteForceProtectionService.isAccountLocked(username));
    }


    @Test
    public void testIsAccountLocked() {
        // Arrange
        String username = "user3";

        // Act
        boolean lockedInitially = bruteForceProtectionService.isAccountLocked(username);

        // Assert
        assertFalse(lockedInitially);
    }

    @Test
    public void testLoginSucceeded() {
        // Arrange
        String username = "user4";
        bruteForceProtectionService.loginFailed(username); // Simulate failed attempt before success

        // Act
        bruteForceProtectionService.loginSucceeded(username);

        // Assert
        assertFalse(bruteForceProtectionService.isAccountLocked(username));
    }

    @Test
    public void testLoginFailedWithResetAttempts() {
        // Arrange
        String username = "user2";
        bruteForceProtectionService.loginFailed(username); // 1st failed attempt
        bruteForceProtectionService.loginFailed(username); // 2nd failed attempt

        // Act
        bruteForceProtectionService.loginFailed(username); // 3rd failed attempt, account should be locked

        // Assert
        assertTrue(bruteForceProtectionService.isAccountLocked(username));

        // Act: Resetting attempts after the lock duration
        sleep(1000 * 60*5); // Sleep for 1 minute to reset attempts
        bruteForceProtectionService.loginFailed(username); // 1st failed attempt after reset

        // Assert: Account should not be locked after reset attempts
        assertFalse(bruteForceProtectionService.isAccountLocked(username));
    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
