package com.github.jdussouillez.testapp.service;

import com.github.jdussouillez.testapp.Loggers;
import com.github.jdussouillez.testapp.oidc.OidcTokenProvider;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TestService {

    @Inject
    protected OidcTokenProvider oidcTokenProvider;

    public Multi<String> fetch() {
        return oidcTokenProvider.getFooBarToken()
            .onItem()
            .transformToMulti(token -> {
                Loggers.MAIN.info("Token: {}", token);
                return Multi.createFrom().items("foo", "bar", "baz");
            });
    }
}
