package com.onload.domain.services;

import java.util.List;

import com.onload.domain.models.Cliente;
import com.onload.domain.repositories.ClienteRepository;

import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class ClienteService {
    
    ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository _clienteRepository) {
        this.clienteRepository = _clienteRepository;
    }


    public List<Cliente> findAll(){
        return clienteRepository.listAll();
    }
}
