package com.onload.web.controllers;

import com.onload.web.dtos.*;
import com.onload.domain.services.LojaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.List;

@Path("/lojas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Lojas", description = "Endpoints para gestão de lojas")
public class LojaResource {

    @Inject
    LojaService service;

    @GET
    @Operation(summary = "Lista todas as lojas")
    public List<LojaDTO> listar() {
        return service.listarTodas();
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Busca loja pelo ID")
    public LojaDTO buscar(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    }

    @POST
    @Operation(summary = "Cria uma nova loja")
    public Response criar(@Valid LojaDTO dto) {
        var criada = service.criar(dto);
        return Response.created(URI.create("/lojas/" + criada.id()))
                .entity(criada)
                .build();
    }

    @PUT
    @Path("{id}")
    @Operation(summary = "Atualiza uma loja existente")
    public LojaDTO atualizar(@PathParam("id") Long id, @Valid LojaDTO dto) {
        return service.atualizar(id, dto);
    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Remove uma loja")
    public void remover(@PathParam("id") Long id) {
        service.remover(id);
    }
}