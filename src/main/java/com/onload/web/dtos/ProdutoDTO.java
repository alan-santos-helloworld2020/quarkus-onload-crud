package com.onload.web.dtos;

import java.math.BigDecimal;

public record ProdutoDTO(
    Long id,
    String nome,
    String descricao,
    BigDecimal preco,
    Integer quantidade,
    Long fornecedorId
) {}