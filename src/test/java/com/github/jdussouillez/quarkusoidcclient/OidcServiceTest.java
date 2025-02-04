package com.github.jdussouillez.quarkusoidcclient;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class OidcServiceTest {

    @Inject
    protected OidcService oidcService;

    @Test
    public void test() {
        // Nothing to do, we just want Quarkus to start
    }
}
