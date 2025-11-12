package com.onload.web.controllers;

import com.onload.domain.services.AuthService;
import com.onload.web.dtos.LoginRequest;
import com.onload.web.dtos.TokenResponse;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Autenticação", description = "Endpoints para geração de token JWT")
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    @Operation(summary = "Autentica um funcionário e gera um token JWT")
    public TokenResponse login(@Valid LoginRequest request) {
        return authService.authenticate(request);
    }
}
