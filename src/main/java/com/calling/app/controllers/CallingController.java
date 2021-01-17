package com.calling.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calling.app.services.CallingService;

import reactor.core.publisher.Mono;

import static com.calling.app.utils.Constants.CALLING_SERVICE_BASE_URL;
import static com.calling.app.utils.Constants.CALLING_SERVICE_URL;;

@RestController
@RequestMapping(CALLING_SERVICE_BASE_URL)
public class CallingController {
	private final CallingService callingService;

	public CallingController(CallingService callingService) {
		this.callingService = callingService;
	}

	@GetMapping(CALLING_SERVICE_URL)
	public Mono<String> getHello() {
		return callingService.getHello();
	}

}