package com.onload.web.dtos;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "LojaDTO", description = "Representa uma loja")
public record LojaDTO(
        Long id,
        String nome,
        String endereco,
        String cnpj) {
}
