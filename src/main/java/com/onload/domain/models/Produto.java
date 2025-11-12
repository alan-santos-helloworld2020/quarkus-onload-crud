package com.onload.domain.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.math.BigDecimal;

@Entity
@Table(name = "produto")
@Schema(name = "Produto", description = "Entidade que representa um produto cadastrado por um fornecedor")
public class Produto extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", description = "Identificador único do produto")
    public Long id;

    @NotBlank(message = "O nome do produto é obrigatório")
    @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres")
    @Column(nullable = false, length = 120)
    @Schema(example = "Notebook Dell Inspiron 15", description = "Nome ou descrição principal do produto")
    public String nome;

    @Size(max = 255)
    @Schema(example = "Notebook com processador i7 e 16GB de RAM", description = "Descrição detalhada do produto")
    public String descricao;

    @NotNull(message = "O preço é obrigatório")
    @Column(nullable = false, precision = 10, scale = 2)
    @Schema(example = "4999.90", description = "Preço do produto")
    public BigDecimal preco;

    @NotNull(message = "A quantidade é obrigatória")
    @Column(nullable = false)
    @Schema(example = "10", description = "Quantidade disponível em estoque")
    public Integer quantidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    @Schema(description = "Fornecedor responsável pelo produto")
    @JsonManagedReference
    public Fornecedor fornecedor;
}
