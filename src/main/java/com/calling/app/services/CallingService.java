package com.calling.app.services;

import reactor.core.publisher.Mono;

public interface CallingService {
	public Mono<String> getHello();
}
