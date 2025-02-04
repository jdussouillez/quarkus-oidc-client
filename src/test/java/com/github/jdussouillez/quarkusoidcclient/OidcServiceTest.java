package com.github.jdussouillez.quarkusoidcclient;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class OidcServiceTest {

    @Inject
    protected OidcService oidcService;

    @Test
    public void testGetFooAccessToken() {
        /*
        var accessToken = oidcService.getFooAccessToken()
            .await()
            .atMost(Duration.ofSeconds(10));
        assertNotNull(accessToken);
         */
    }
}
