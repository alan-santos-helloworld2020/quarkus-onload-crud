package com.onload.domain.services;

import java.util.List;
import java.util.Optional;

import com.onload.domain.models.Cliente;
import com.onload.domain.repositories.ClienteRepository;
import com.onload.web.dtos.ClienteDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ClienteService {

    ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository _clienteRepository) {
        this.clienteRepository = _clienteRepository;
    }

    public List<Cliente> findAll() {
        return clienteRepository.listAll();
    }

    public Optional<Cliente> findById(Long id) {
        var res = clienteRepository.findByIdOptional(id);
        return res;
    }

    @Transactional
    public ClienteDTO savCliente(ClienteDTO clienteDto) {

        clienteRepository.findByEmail(clienteDto.email()).ifPresent(x -> {
            throw conflict("E-mail já cadastrado");
        });

        var cl = new Cliente();
        cl.nome = clienteDto.nome();
        cl.email = clienteDto.email();
        cl.telefone = clienteDto.telefone();
        cl.cep = clienteDto.cep();

        clienteRepository.persist(cl);
        clienteRepository.flush();

        return clienteDto;
    }

    private WebApplicationException notFound(String msg) {
        return new WebApplicationException(msg, Response.Status.NOT_FOUND);
    }

    private WebApplicationException conflict(String msg) {
        return new WebApplicationException(msg, Response.Status.CONFLICT);
    }
}
