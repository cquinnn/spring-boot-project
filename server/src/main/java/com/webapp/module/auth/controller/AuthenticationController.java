package com.webapp.module.auth.controller;

import com.nimbusds.jose.JOSEException;
import com.webapp.common.dto.response.ApiResponse;
import com.webapp.module.auth.dto.request.AuthenticationRequest;
import com.webapp.module.auth.dto.request.IntrospectRequest;
import com.webapp.module.auth.dto.request.LogoutRequest;
import com.webapp.module.auth.dto.response.AuthenticationResponse;
import com.webapp.module.auth.dto.response.IntrospectResponse;
import com.webapp.module.auth.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @GetMapping("/google")
    public ApiResponse<?> loginWithGoogle(@AuthenticationPrincipal OAuth2User oAuth2User) {
        var result = authenticationService.authenticateByGoogle(oAuth2User);

        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws JOSEException, ParseException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws JOSEException, ParseException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }

}
