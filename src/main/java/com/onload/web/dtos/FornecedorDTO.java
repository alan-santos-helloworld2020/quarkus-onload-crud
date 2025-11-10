package com.onload.web.dtos;

public record FornecedorDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String endereco,
        String cnpj,
        String cep,
        Long lojaId) {
}