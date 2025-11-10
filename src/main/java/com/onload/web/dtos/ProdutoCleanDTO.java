package com.onload.web.dtos;

import java.math.BigDecimal;



public record ProdutoCleanDTO(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer quantidade,
        FornecedorDTO fornecedorDto) {

}
