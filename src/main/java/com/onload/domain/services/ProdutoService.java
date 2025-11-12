package com.onload.domain.services;



import com.onload.domain.models.Produto;
import com.onload.web.dtos.ProdutoDTO;
import com.onload.domain.repositories.ProdutoRepository;
import com.onload.domain.repositories.FornecedorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class ProdutoService {

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    FornecedorRepository fornecedorRepository;

    public List<ProdutoDTO> listarTodos() {
        return produtoRepository.listAll().stream()
                .map(this::toDTO)
                .toList();

    }

    public List<ProdutoDTO> listarPorFornecedor(Long fornecedorId) {
        return produtoRepository.listByFornecedor(fornecedorId).stream()
                .map(this::toDTO)
                .toList();
    }

    public ProdutoDTO buscarPorId(Long id) {
        var p = produtoRepository.findById(id);
        if (p == null)
            throw new WebApplicationException("Produto não encontrado", Response.Status.NOT_FOUND);
        return toDTO(p);
    }

    @Transactional
    public ProdutoDTO criar(ProdutoDTO dto) {
        var fornecedor = fornecedorRepository.findById(dto.fornecedorId());
        if (fornecedor == null)
            throw new WebApplicationException("Fornecedor não encontrado", Response.Status.NOT_FOUND);

        var p = new Produto();
        p.nome = dto.nome();
        p.descricao = dto.descricao();
        p.preco = dto.preco();
        p.quantidade = dto.quantidade();
        p.fornecedor = fornecedor;

        produtoRepository.persist(p);
        produtoRepository.flush();

        return toDTO(p);
    }

    @Transactional
    public ProdutoDTO atualizar(Long id, ProdutoDTO dto) {
        var p = produtoRepository.findById(id);
        if (p == null)
            throw new WebApplicationException("Produto não encontrado", Response.Status.NOT_FOUND);

        p.nome = dto.nome();
        p.descricao = dto.descricao();
        p.preco = dto.preco();
        p.quantidade = dto.quantidade();
        p.fornecedor = fornecedorRepository.findById(dto.fornecedorId());
        

        return toDTO(p);
    }

    @Transactional
    public void remover(Long id) {
        if (!produtoRepository.deleteById(id))
            throw new WebApplicationException("Produto não encontrado", Response.Status.NOT_FOUND);
    }

    private ProdutoDTO toDTO(Produto p) {
        return new ProdutoDTO(p.id, p.nome, p.descricao, p.preco, p.quantidade,
                p.fornecedor != null ? p.fornecedor.id : null);
    }    

    
}
