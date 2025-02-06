package com.github.jdussouillez.testapp.oidc;

import io.quarkus.oidc.client.OidcClients;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OidcTokenProvider {

    @Inject
    protected OidcClients oidcClients;
}
