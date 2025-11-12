package com.onload.web.controllers;


import com.onload.web.dtos.FuncionarioDTO;
import com.onload.domain.services.FuncionarioService;
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

@Path("/funcionarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Funcionários", description = "Endpoints para gestão de funcionários")
@RolesAllowed("USER")
public class FuncionarioResource {

    @Inject
    FuncionarioService service;

    @GET
    @Operation(summary = "Lista todos os funcionários")
    public List<FuncionarioDTO> listar() {
        return service.listarTodos();
    }

    @GET
    @Path("/por-loja/{lojaId}")
    @Operation(summary = "Lista funcionários de uma loja específica")
    public List<FuncionarioDTO> listarPorLoja(@PathParam("lojaId") Long lojaId) {
        return service.listarPorLoja(lojaId);
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Busca um funcionário pelo ID")
    public FuncionarioDTO buscar(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    }

    @POST
    @Operation(summary = "Cria um novo funcionário")
    public Response criar(@Valid FuncionarioDTO dto) {
        var criado = service.criar(dto);
        return Response.created(URI.create("/funcionarios/" + criado.id()))
                .entity(criado)
                .build();
    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Atualiza um funcionário existente")
    public FuncionarioDTO atualizar(@PathParam("id") Long id, @Valid FuncionarioDTO dto) {
        return service.atualizar(id, dto);
    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Remove um funcionário pelo ID")
    public void remover(@PathParam("id") Long id) {
        service.remover(id);
    }
}
