package com.onload.domain.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "funcionario")
@Schema(name = "Funcionario", description = "Entidade de funcionário vinculado a uma loja")
public class Funcionario extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", description = "Identificador único do funcionário")
    public Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 120)
    @Schema(example = "João da Silva", description = "Nome do funcionário")
    public String nome;

    @NotBlank(message = "O sexo é obrigatório")
    @Schema(example = "M", description = "Sexo do funcionário (M/F)")
    @Column(length = 1, nullable = false)
    public String sexo;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Column(unique = true, nullable = false, length = 160)
    @Schema(example = "joao.silva@onload.com", description = "E-mail corporativo do funcionário")
    public String email;

    @Size(max = 20)
    @Schema(example = "21999990000", description = "Telefone do funcionário")
    public String telefone;

    @Size(max = 9)
    @Schema(example = "21000-000", description = "CEP do funcionário")
    public String cep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loja_id", nullable = false)
    @Schema(description = "Loja à qual o funcionário pertence")
    @JsonManagedReference
    public Loja loja;
}
