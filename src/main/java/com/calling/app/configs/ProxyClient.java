package com.calling.app.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Configuration
public class ProxyClient {
	private static final Logger log = LoggerFactory.getLogger(ProxyClient.class);

	@SuppressWarnings("deprecation")
	@Bean
	public WebClient getWebClient(@Value("${called.service.base.url}") String url,
			@Value("${web.readTimeout}") Integer readTimeout, @Value("${web.connectionTimeout}") Integer connectTimeout,
			@Value("${web.writeTimeout}") Integer writeTimeout) {
		HttpClient httpClient = HttpClient.create()
				.tcpConfiguration(client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
						.doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(readTimeout))
								.addHandlerLast(new WriteTimeoutHandler(writeTimeout))));

		ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient.wiretap(true));

		return WebClient.builder()
						.baseUrl(url)
						.clientConnector(connector)
						.filter(logRequest())
						.filter(logResponse())
						.build();
	}

	private ExchangeFilterFunction logRequest() {
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
			clientRequest.headers().forEach((name, values) -> values.forEach(value -> {
				log.info("{}={}", name, value);
			}));
			return Mono.just(clientRequest);
		});
	}

	private ExchangeFilterFunction logResponse() {
		return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
			log.info("Request: {} {}", clientResponse.statusCode());
			return Mono.just(clientResponse);
		});
	}
}
