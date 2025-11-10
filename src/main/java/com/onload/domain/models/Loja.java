package com.onload.domain.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

@Entity
@Table(name = "loja")
@Schema(name = "Loja", description = "Entidade de loja que possui vários clientes")
public class Loja extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", description = "Identificador único da loja")
    public Long id;

    @NotBlank(message = "O nome da loja é obrigatório")
    @Size(min = 2, max = 120, message = "O nome da loja deve ter entre 2 e 120 caracteres")
    @Column(nullable = false, length = 120)
    @Schema(example = "Onload Store", description = "Nome da loja")
    public String nome;

    @Size(max = 255)
    @Schema(example = "Av. Brasil, 1000", description = "Endereço da loja")
    public String endereco;

    @Size(max = 14)
    @Column(unique = true)
    @Schema(example = "12.345.678/0001-90", description = "CNPJ da loja")
    public String cnpj;

    @OneToMany(mappedBy = "loja", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Schema(description = "Lista de clientes vinculados à loja")
    @JsonBackReference
    public List<Cliente> clientes;
}
