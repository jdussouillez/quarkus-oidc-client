package com.github.jdussouillez.testapp.oidc;

import io.quarkus.oidc.client.OidcClient;
import io.quarkus.oidc.client.OidcClients;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApplicationScoped
public class OidcTokenProvider {

    private static final String FOO_OIDC_ID = "foo";

    private static final List<String> OIDC_IDS = List.of(
        FOO_OIDC_ID
    );

    @Inject
    protected OidcClients oidcClients;

    protected Map<String, OidcClient> clients;

    @PostConstruct
    protected void init() {
        clients = OIDC_IDS
            .stream()
            .collect(Collectors.toUnmodifiableMap(Function.identity(), oidcClients::getClient));
    }

    // ...
}
