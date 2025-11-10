package com.onload.domain.repositories;

import com.onload.domain.models.Funcionario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.List;

@ApplicationScoped
public class FuncionarioRepository implements PanacheRepository<Funcionario> {

    public Optional<Funcionario> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    public List<Funcionario> listByLoja(Long lojaId) {
        return find("loja.id", lojaId).list();
    }
}