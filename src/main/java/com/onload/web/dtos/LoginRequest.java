package com.onload.web.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Dados para autenticação e emissão de token JWT")
public record LoginRequest(
        @Schema(example = "joao.silva@onload.com", description = "E-mail corporativo do funcionário")
        @NotBlank
        @Email
        String email,

        @Schema(example = "Joao@123", description = "Senha ou código compartilhado")
        @NotBlank
        @Size(min = 3, max = 120)
        String secret
) {
}
