package com.onload.domain.services;

import com.onload.domain.models.Funcionario;
import com.onload.web.dtos.FuncionarioDTO;
import com.onload.domain.repositories.FuncionarioRepository;
import com.onload.domain.repositories.LojaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class FuncionarioService {

    @Inject
    FuncionarioRepository funcionarioRepository;

    @Inject
    LojaRepository lojaRepository;

    public List<FuncionarioDTO> listarTodos() {
        return funcionarioRepository.listAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public List<FuncionarioDTO> listarPorLoja(Long lojaId) {
        return funcionarioRepository.listByLoja(lojaId).stream()
                .map(this::toDTO)
                .toList();
    }

    public FuncionarioDTO buscarPorId(Long id) {
        var f = funcionarioRepository.findById(id);
        if (f == null)
            throw new WebApplicationException("Funcionário não encontrado", Response.Status.NOT_FOUND);
        return toDTO(f);
    }

    @Transactional
    public FuncionarioDTO criar(FuncionarioDTO dto) {
        lojaRepository.findByIdOptional(dto.lojaId())
                .orElseThrow(() -> new WebApplicationException("Loja não encontrada", Response.Status.NOT_FOUND));

        funcionarioRepository.findByEmail(dto.email()).ifPresent(x -> {
            throw conflict("E-mail já cadastrado");
        });

        var func = new Funcionario();
        func.nome = dto.nome();
        func.sexo = dto.sexo();
        func.email = dto.email();
        func.telefone = dto.telefone();
        func.cep = dto.cep();
        func.loja = lojaRepository.findById(dto.lojaId());

        funcionarioRepository.persist(func);
        funcionarioRepository.flush();

        return toDTO(func);
    }

    @Transactional
    public FuncionarioDTO atualizar(Long id, FuncionarioDTO dto) {
        var func = funcionarioRepository.findById(id);
        if (func == null)
            throw new WebApplicationException("Funcionário não encontrado", Response.Status.NOT_FOUND);

        if (!func.email.equals(dto.email())) {
            funcionarioRepository.findByEmail(dto.email()).ifPresent(x -> {
                throw conflict("E-mail já cadastrado");
            });
        }

        func.nome = dto.nome();
        func.sexo = dto.sexo();
        func.email = dto.email();
        func.telefone = dto.telefone();
        func.cep = dto.cep();
        func.loja = lojaRepository.findById(dto.lojaId());

        return toDTO(func);
    }

    @Transactional
    public void remover(Long id) {
        if (!funcionarioRepository.deleteById(id))
            throw new WebApplicationException("Funcionário não encontrado", Response.Status.NOT_FOUND);
    }

    private FuncionarioDTO toDTO(Funcionario f) {
        return new FuncionarioDTO(f.id, f.nome, f.sexo, f.email, f.telefone, f.cep,
                f.loja != null ? f.loja.id : null);
    }

    private WebApplicationException conflict(String msg) {
        return new WebApplicationException(msg, Response.Status.CONFLICT);
    }
}
