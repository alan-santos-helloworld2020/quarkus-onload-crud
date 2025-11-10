package com.onload.domain.services;



import com.onload.domain.models.Fornecedor;
import com.onload.domain.models.Loja;
import com.onload.web.dtos.FornecedorDTO;
import com.onload.domain.repositories.FornecedorRepository;
import com.onload.domain.repositories.LojaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class FornecedorService {

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    LojaRepository lojaRepository;

    public List<FornecedorDTO> listarTodos() {
        return fornecedorRepository.listAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public List<FornecedorDTO> listarPorLoja(Long lojaId) {
        return fornecedorRepository.listByLoja(lojaId).stream()
                .map(this::toDTO)
                .toList();
    }

    public FornecedorDTO buscarPorId(Long id) {
        var f = fornecedorRepository.findById(id);
        if (f == null)
            throw new WebApplicationException("Fornecedor não encontrado", Response.Status.NOT_FOUND);
        return toDTO(f);
    }

    @Transactional
    public FornecedorDTO criar(FornecedorDTO dto) {
        Loja loja = lojaRepository.findById(dto.lojaId());
        if (loja == null)
            throw new WebApplicationException("Loja não encontrada", Response.Status.NOT_FOUND);

        fornecedorRepository.findByEmail(dto.email()).ifPresent(x -> {
            throw conflict("E-mail já cadastrado");
        });

        fornecedorRepository.findByCnpj(dto.cnpj()).ifPresent(x -> {
            throw conflict("CNPJ já cadastrado");
        });

        var f = new Fornecedor();
        f.nome = dto.nome();
        f.email = dto.email();
        f.telefone = dto.telefone();
        f.endereco = dto.endereco();
        f.cnpj = dto.cnpj();
        f.cep = dto.cep();
        f.loja = loja;

        fornecedorRepository.persist(f);
        fornecedorRepository.flush();

        return toDTO(f);
    }

    @Transactional
    public FornecedorDTO atualizar(Long id, FornecedorDTO dto) {
        var f = fornecedorRepository.findById(id);
        if (f == null)
            throw new WebApplicationException("Fornecedor não encontrado", Response.Status.NOT_FOUND);

        if (!f.email.equals(dto.email())) {
            fornecedorRepository.findByEmail(dto.email()).ifPresent(x -> {
                throw conflict("E-mail já cadastrado");
            });
        }

        if (!f.cnpj.equals(dto.cnpj())) {
            fornecedorRepository.findByCnpj(dto.cnpj()).ifPresent(x -> {
                throw conflict("CNPJ já cadastrado");
            });
        }

        f.nome = dto.nome();
        f.email = dto.email();
        f.telefone = dto.telefone();
        f.endereco = dto.endereco();
        f.cnpj = dto.cnpj();
        f.cep = dto.cep();
        f.loja = lojaRepository.findById(dto.lojaId());

        return toDTO(f);
    }

    @Transactional
    public void remover(Long id) {
        if (!fornecedorRepository.deleteById(id))
            throw new WebApplicationException("Fornecedor não encontrado", Response.Status.NOT_FOUND);
    }

    private FornecedorDTO toDTO(Fornecedor f) {
        return new FornecedorDTO(f.id, f.nome, f.email, f.telefone, f.endereco, f.cnpj, f.cep,
                f.loja != null ? f.loja.id : null);
    }

    private WebApplicationException conflict(String msg) {
        return new WebApplicationException(msg, Response.Status.CONFLICT);
    }
}
