package com.onload.domain.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;



@Entity
@Table(name = "cliente")
public class Cliente extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, length = 120)
    public String nome;

    @Column(unique = true, nullable = false, length = 160)
    public String email;

    public String telefone;
    public String cep;

    // RELACIONAMENTO COM LOJA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loja_id", nullable = false)
    @JsonManagedReference
    public Loja loja;
}
