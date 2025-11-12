package com.onload.domain.services;

import com.onload.domain.repositories.FuncionarioRepository;
import com.onload.web.dtos.LoginRequest;
import com.onload.web.dtos.TokenResponse;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Instant;
import java.util.Set;

@ApplicationScoped
public class AuthService {

    @Inject
    FuncionarioRepository funcionarioRepository;

    @ConfigProperty(name = "security.auth.shared-secret")
    String sharedSecret;

    @ConfigProperty(name = "security.auth.token-expiration-seconds", defaultValue = "3600")
    long expirationSeconds;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    public TokenResponse authenticate(LoginRequest request) {
        if (!sharedSecret.equals(request.secret())) {
            throw new WebApplicationException("Credenciais inválidas", Response.Status.UNAUTHORIZED);
        }

        var email = request.email().trim();
        var funcionario = funcionarioRepository.findByEmail(email)
                .orElseThrow(() -> new WebApplicationException("Usuário não encontrado", Response.Status.UNAUTHORIZED));

        var expiresAt = Instant.now().plusSeconds(expirationSeconds);
        JwtClaimsBuilder claims = Jwt.claims();
        claims.issuer(issuer);
        claims.subject(funcionario.id != null ? funcionario.id.toString() : funcionario.email);
        claims.upn(funcionario.email);
        claims.groups(Set.of("USER"));
        claims.claim("name", funcionario.nome);
        if (funcionario.loja != null && funcionario.loja.id != null) {
            claims.claim("lojaId", funcionario.loja.id);
        }
        claims.expiresAt(expiresAt);

        var token = claims.sign();
        return new TokenResponse("Bearer", token, expirationSeconds);
    }
}
