package com.onload.domain.services;

import com.onload.domain.models.Loja;
import com.onload.web.dtos.*;
import com.onload.domain.repositories.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class LojaService {

    @Inject
    LojaRepository repository;

    public List<LojaDTO> listarTodas() {
        return repository.listAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public LojaDTO buscarPorId(Long id) {
        var loja = repository.findById(id);
        if (loja == null)
            throw new WebApplicationException("Loja não encontrada", Response.Status.NOT_FOUND);
        return toDTO(loja);
    }

    @Transactional
    public LojaDTO criar(LojaDTO dto) {
        repository.findByCnpj(dto.cnpj()).ifPresent(x -> {
            throw conflict("CNPJ já cadastrado");
        });

        var loja = new Loja();
        loja.nome = dto.nome();
        loja.endereco = dto.endereco();
        loja.cnpj = dto.cnpj();
        repository.persist(loja);
        repository.flush();

        return toDTO(loja);
    }

    @Transactional
    public LojaDTO atualizar(Long id, LojaDTO dto) {
        var loja = repository.findById(id);
        if (loja == null)
            throw new WebApplicationException("Loja não encontrada", Response.Status.NOT_FOUND);

        // se mudar o CNPJ, checar duplicidade
        if (!loja.cnpj.equals(dto.cnpj())) {
            repository.findByCnpj(dto.cnpj()).ifPresent(x -> {
                throw conflict("CNPJ já cadastrado");
            });
        }

        loja.nome = dto.nome();
        loja.endereco = dto.endereco();
        loja.cnpj = dto.cnpj();

        return toDTO(loja);
    }

    @Transactional
    public void remover(Long id) {
        if (!repository.deleteById(id))
            throw new WebApplicationException("Loja não encontrada", Response.Status.NOT_FOUND);
    }

    private LojaDTO toDTO(Loja loja) {
        return new LojaDTO(loja.id, loja.nome, loja.endereco, loja.cnpj);
    }

    private WebApplicationException conflict(String msg) {
        return new WebApplicationException(msg, Response.Status.CONFLICT);
    }
}