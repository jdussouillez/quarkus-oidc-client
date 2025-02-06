package com.github.jdussouillez.testapp.oidc;

import com.github.jdussouillez.testapp.Loggers;
import io.quarkus.oidc.client.OidcClient;
import io.quarkus.oidc.client.OidcClients;
import io.quarkus.oidc.client.Tokens;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
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

    protected final Map<String, Tokens> tokens = new ConcurrentHashMap<>(OIDC_IDS.size(), 1f);

    @PostConstruct
    protected void init() {
        clients = OIDC_IDS
            .stream()
            .collect(Collectors.toUnmodifiableMap(Function.identity(), oidcClients::getClient));
    }

    public Uni<String> getFooToken() {
        return updateToken(FOO_OIDC_ID)
            .map(Tokens::getAccessToken);
    }

    private Uni<Tokens> updateToken(final String clientId) {
        var client = clients.get(clientId);
        if (client == null) {
            return Uni.createFrom().failure(() -> new NoSuchElementException("Client not found: " + clientId));
        }
        var token = tokens.get(clientId);
        if (token == null || token.isAccessTokenExpired()) {
            return client.getTokens()
                .invoke(t -> tokens.put(clientId, t));
        }
        return Uni.createFrom().item(token);
    }
}
