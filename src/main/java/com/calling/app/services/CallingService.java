package com.calling.app.services;

import com.calling.app.models.HelloWorldResponse;

import reactor.core.publisher.Mono;

public interface CallingService {
	public Mono<HelloWorldResponse> getHello();
}
