package com.onload.domain.services;

import java.util.List;
import java.util.Optional;

import com.onload.domain.models.Cliente;

import com.onload.domain.repositories.ClienteRepository;
import com.onload.domain.repositories.LojaRepository;
import com.onload.web.dtos.ClienteDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ClienteService {

    ClienteRepository clienteRepository;
    LojaRepository lojReLojaRepository;

    public ClienteService(ClienteRepository _clienteRepository, LojaRepository _lojReLojaRepository) {
        this.clienteRepository = _clienteRepository;
        this.lojReLojaRepository = _lojReLojaRepository;
    }

    public List<Cliente> findAll() {
        return clienteRepository.listAll();
    }

    public Optional<Cliente> findById(Long id) {
        var cliente = clienteRepository.findByIdOptional(id);
        if (!cliente.isPresent())
            throw new WebApplicationException("cliente não encontrado", Response.Status.NOT_FOUND);

        return cliente;
    }

    @Transactional
    public ClienteDTO saveCliente(ClienteDTO clienteDto) {

        var loja = lojReLojaRepository.findById(clienteDto.lojaId());
        if (loja == null)
            throw notFound("Cliente não encontrado");

        clienteRepository.findByEmail(clienteDto.email()).ifPresent(x -> {
            throw conflict("E-mail já cadastrado");
        });

        var cl = new Cliente();
        cl.nome = clienteDto.nome();
        cl.email = clienteDto.email();
        cl.telefone = clienteDto.telefone();
        cl.cep = clienteDto.cep();
        cl.loja = loja;

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
