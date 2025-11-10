package com.onload.domain.repositories;


import com.onload.domain.models.Fornecedor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.List;

@ApplicationScoped
public class FornecedorRepository implements PanacheRepository<Fornecedor> {

    public Optional<Fornecedor> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    public Optional<Fornecedor> findByCnpj(String cnpj) {
        return find("cnpj", cnpj).firstResultOptional();
    }

    public List<Fornecedor> listByLoja(Long lojaId) {
        return find("loja.id", lojaId).list();
    }
}
