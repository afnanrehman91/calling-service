package com.calling.app.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.calling.app.exceptions.BadRequestException;
import com.calling.app.exceptions.InternalServerException;
import com.calling.app.models.HelloWorldResponse;

import io.github.resilience4j.retry.annotation.Retry;
import reactor.core.publisher.Mono;

import static com.calling.app.utils.Constants.BAD_REQUEST_EXCEPTION;
import static com.calling.app.utils.Constants.INTERNAL_SERVER_EXCEPTION;;

@Service
public class CallingServiceImpl implements CallingService {
	private final WebClient webClient;
	private final String uri;

	public CallingServiceImpl(WebClient webClient, @Value("${called.service.uri}") String uri) {
		this.webClient = webClient;
		this.uri = uri;
	}

	@Override
	@Retry(name = "callerService")
	public Mono<HelloWorldResponse> getHello() {
		return webClient.get().uri(uri).retrieve()
				.onStatus(HttpStatus::is4xxClientError,
						clientResponse -> Mono.error(new BadRequestException(BAD_REQUEST_EXCEPTION)))
				.onStatus(HttpStatus::is5xxServerError,
						clientResponse -> Mono.error(new InternalServerException(INTERNAL_SERVER_EXCEPTION)))
				.bodyToMono(HelloWorldResponse.class);
	}

}
