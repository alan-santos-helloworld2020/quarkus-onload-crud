package com.onload.web.dtos;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "FuncionarioDTO", description = "Representa um funcionário vinculado a uma loja")
public record FuncionarioDTO(
        Long id,
        String nome,
        String sexo,
        String email,
        String telefone,
        String cep,
        Long lojaId) {
}