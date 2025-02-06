package com.github.jdussouillez.testapp.service;

import com.github.jdussouillez.testapp.oidc.OidcTokenProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TestService {

    @Inject
    protected OidcTokenProvider oidcTokenProvider;
}
