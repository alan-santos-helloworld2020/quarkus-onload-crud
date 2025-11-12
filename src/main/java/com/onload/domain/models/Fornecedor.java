package com.onload.domain.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "fornecedor")
@Schema(name = "Fornecedor", description = "Entidade que representa um fornecedor vinculado a uma loja")
public class Fornecedor extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", description = "Identificador único do fornecedor")
    public Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 120, message = "O nome deve ter entre 2 e 120 caracteres")
    @Column(nullable = false, length = 120)
    @Schema(example = "Onload Distribuidora", description = "Nome do fornecedor")
    public String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Column(unique = true, nullable = false, length = 160)
    @Schema(example = "contato@onloaddistribuidora.com", description = "E-mail de contato do fornecedor")
    public String email;

    @Size(max = 20)
    @Schema(example = "2133334444", description = "Telefone comercial do fornecedor")
    public String telefone;

    @Size(max = 255)
    @Schema(example = "Av. Brasil, 5000", description = "Endereço completo do fornecedor")
    public String endereco;

    @Size(max = 14)
    @Column(unique = true)
    @Schema(example = "12.345.678/0001-90", description = "CNPJ do fornecedor")
    public String cnpj;

    @Size(max = 9)
    @Schema(example = "21000-000", description = "CEP do fornecedor")
    public String cep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loja_id", nullable = false)
    @Schema(description = "Loja à qual o fornecedor está vinculado")
    public Loja loja;

    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Schema(description = "Lista de produtos vinculados ao fornecedor")
    @JsonBackReference
    public List<Produto> produtos;


}
