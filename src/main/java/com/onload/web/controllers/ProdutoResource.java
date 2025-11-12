package com.onload.web.controllers;


import com.onload.web.dtos.ProdutoDTO;
import com.onload.domain.services.ProdutoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.List;

@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Produtos", description = "Endpoints para gestão de produtos vinculados a fornecedores")
@RolesAllowed("USER")
public class ProdutoResource {

    @Inject
    ProdutoService service;

    @GET
    @Operation(summary = "Lista todos os produtos")
    public List<ProdutoDTO> listar() {
        return service.listarTodos();
    }

    @GET
    @Path("/por-fornecedor/{fornecedorId}")
    @Operation(summary = "Lista produtos de um fornecedor específico")
    public List<ProdutoDTO> listarPorFornecedor(@PathParam("fornecedorId") Long fornecedorId) {
        return service.listarPorFornecedor(fornecedorId);
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Busca um produto pelo ID")
    public ProdutoDTO buscar(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    }

    @POST
    @Operation(summary = "Cria um novo produto")
    public Response criar(@Valid ProdutoDTO dto) {
        var criado = service.criar(dto);
        return Response.created(URI.create("/produtos/" + criado.id()))
                .entity(criado)
                .build();
    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Atualiza um produto existente")
    public ProdutoDTO atualizar(@PathParam("id") Long id, @Valid ProdutoDTO dto) {
        return service.atualizar(id, dto);
    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Remove um produto pelo ID")
    public void remover(@PathParam("id") Long id) {
        service.remover(id);
    }
}
