package com.webapp.identity.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.webapp.identity.dto.request.ApiResponse;
import com.webapp.identity.dto.request.AuthenticationRequest;
import com.webapp.identity.dto.request.IntrospectRequest;
import com.webapp.identity.dto.request.LogoutRequest;
import com.webapp.identity.dto.response.AuthenticationResponse;
import com.webapp.identity.dto.response.IntrospectResponse;
import com.webapp.identity.service.AuthenticationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
	AuthenticationService authenticationService;
	
	@PostMapping("/log-in")
	ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
		var result = authenticationService.authenticate(request);
		return ApiResponse.<AuthenticationResponse>builder()
				.result(result)
				.build();
	}
	
	@PostMapping("/introspect")
	ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws JOSEException, ParseException{
		var result = authenticationService.introspect(request);
		return ApiResponse.<IntrospectResponse>builder()
				.result(result)
				.build();
	}
	
	@PostMapping("/logout")
	ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws JOSEException, ParseException {
		authenticationService.logout(request);
		return ApiResponse.<Void>builder().build();
	}
}
