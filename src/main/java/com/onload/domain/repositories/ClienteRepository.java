package com.onload.domain.repositories;

import java.util.Optional;

import com.onload.domain.models.Cliente;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public Optional<Cliente> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

}
