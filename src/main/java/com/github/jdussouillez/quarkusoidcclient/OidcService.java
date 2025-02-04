package com.github.jdussouillez.quarkusoidcclient;

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
public class OidcService {

    private static final String FOO_ID = "foo";

    private static final List<String> OIDC_IDS = List.of(
        FOO_ID
        // ...
    );

    @Inject
    protected OidcClients oidcClients;

    protected Map<String, OidcClient> clients;

    protected volatile Map<String, Tokens> tokens = new ConcurrentHashMap<>(OIDC_IDS.size());

    public Uni<String> getFooAccessToken() {
        return getOrUpdateToken(FOO_ID).map(Tokens::getAccessToken);
    }

    @PostConstruct
    protected void initClients() {
        clients = OIDC_IDS.stream()
            .collect(Collectors.toUnmodifiableMap(Function.identity(), oidcClients::getClient));
    }

    private Uni<Tokens> getOrUpdateToken(final String clientId) {
        var client = clients.get(clientId);
        if (client == null) {
            throw new NoSuchElementException("Client " + clientId + " doesn't exist");
        }
        var token = tokens.get(clientId);
        if (token == null || token.isAccessTokenExpired()) {
            return client.getTokens()
                .invoke(t -> tokens.put(clientId, t));
        }
        return Uni.createFrom().item(token);
    }
}
