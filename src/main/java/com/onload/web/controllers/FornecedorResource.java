package com.onload.web.controllers;



import com.onload.web.dtos.FornecedorDTO;
import com.onload.domain.services.FornecedorService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.List;

@Path("/fornecedores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Fornecedores", description = "Endpoints para gestão de fornecedores")
public class FornecedorResource {

    @Inject
    FornecedorService service;

    @GET
    @Operation(summary = "Lista todos os fornecedores")
    public List<FornecedorDTO> listar() {
        return service.listarTodos();
    }

    @GET
    @Path("/por-loja/{lojaId}")
    @Operation(summary = "Lista fornecedores de uma loja específica")
    public List<FornecedorDTO> listarPorLoja(@PathParam("lojaId") Long lojaId) {
        return service.listarPorLoja(lojaId);
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Busca um fornecedor pelo ID")
    public FornecedorDTO buscar(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    }

    @POST
    @Operation(summary = "Cria um novo fornecedor")
    public Response criar(@Valid FornecedorDTO dto) {
        var criado = service.criar(dto);
        return Response.created(URI.create("/fornecedores/" + criado.id()))
                .entity(criado)
                .build();
    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Atualiza um fornecedor existente")
    public FornecedorDTO atualizar(@PathParam("id") Long id, @Valid FornecedorDTO dto) {
        return service.atualizar(id, dto);
    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Remove um fornecedor pelo ID")
    public void remover(@PathParam("id") Long id) {
        service.remover(id);
    }
}
