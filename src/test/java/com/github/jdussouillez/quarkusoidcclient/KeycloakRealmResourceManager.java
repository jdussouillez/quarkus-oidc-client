package com.github.jdussouillez.quarkusoidcclient;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import java.util.Map;

public class KeycloakRealmResourceManager { //implements QuarkusTestResourceLifecycleManager {

    private WireMockServer server;

    //@Override
    public Map<String, String> start() {
        server = new WireMockServer(
            WireMockConfiguration.wireMockConfig()
                .dynamicPort()
                .useChunkedTransferEncoding(Options.ChunkedEncodingPolicy.NEVER)
        );
        server.start();

        server.stubFor(
            WireMock.post("/tokens")
                .withRequestBody(WireMock.matching("grant_type=password&username=alice&password=alice"))
                .willReturn(
                    WireMock
                        .aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"access_token\":\"access_token_1\", \"expires_in\":4, \"refresh_token\":\"refresh_token_1\"}")
                )
        );
        server.stubFor(
            WireMock.post("/tokens")
                .withRequestBody(WireMock.matching("grant_type=refresh_token&refresh_token=refresh_token_1"))
                .willReturn(
                    WireMock
                        .aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"access_token\":\"access_token_2\", \"expires_in\":4, \"refresh_token\":\"refresh_token_1\"}")
                )
        );
        return Map.of("keycloak.url", server.baseUrl());
    }

    //@Override
    public synchronized void stop() {
        if (server != null) {
            server.stop();
            server = null;
        }
    }
}
