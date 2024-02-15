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
        String username = "user1";

        bruteForceProtectionService.loginFailed(username);

        assertFalse(bruteForceProtectionService.isAccountLocked(username));
    }


    @Test
    public void testIsAccountLocked() {
        String username = "user3";

        boolean lockedInitially = bruteForceProtectionService.isAccountLocked(username);

        assertFalse(lockedInitially);
    }

    @Test
    public void testLoginSucceeded() {
        String username = "user4";
        bruteForceProtectionService.loginFailed(username);

        bruteForceProtectionService.loginSucceeded(username);

        assertFalse(bruteForceProtectionService.isAccountLocked(username));
    }

    @Test
    public void testLoginFailedWithResetAttempts() {
        String username = "user2";
        bruteForceProtectionService.loginFailed(username);
        bruteForceProtectionService.loginFailed(username);

        bruteForceProtectionService.loginFailed(username);

        assertTrue(bruteForceProtectionService.isAccountLocked(username));
        //commented just to not to take so much time
//        sleep(1000 * 60*5);

        bruteForceProtectionService.loginFailed(username);

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
