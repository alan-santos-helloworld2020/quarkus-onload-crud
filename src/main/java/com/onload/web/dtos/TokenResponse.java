package com.onload.web.dtos;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Token JWT emitido pela API")
public record TokenResponse(
        @Schema(example = "Bearer")
        String type,
        @Schema(description = "Token JWT assinado para uso no header Authorization")
        String token,
        @Schema(example = "3600", description = "Tempo em segundos até a expiração")
        long expiresInSeconds
) {
}
