package com.onload.domain.repositories;

import java.util.Optional;

import com.onload.domain.models.Loja;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class LojaRepository implements PanacheRepository<Loja> {

    public Optional<Loja> findByCnpj(String cnpj) {
        return find("cnpj", cnpj).firstResultOptional();
    }
}