package com.calling.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calling.app.models.HelloWorldResponse;
import com.calling.app.services.CallingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

	@Operation(description = "This API will return Hello World!", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully detched hello world.", 
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = HelloWorldResponse.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request.", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping(CALLING_SERVICE_URL)
	public Mono<HelloWorldResponse> getHello() {
		return callingService.getHello();
	}

}