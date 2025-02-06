package com.github.jdussouillez.testapp.rpc.service;

import com.github.jdussouillez.testapp.service.TestService;
import io.quarkus.grpc.GrpcService;
import jakarta.inject.Inject;

@GrpcService
public class TestApiService {

    @Inject
    protected TestService testService;
}
