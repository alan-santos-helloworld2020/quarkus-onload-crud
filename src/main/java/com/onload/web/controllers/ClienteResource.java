package com.onload.web.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.eclipse.microprofile.openapi.annotations.Operation;

import com.onload.domain.models.Cliente;
import com.onload.domain.services.ClienteService;
import com.onload.web.dtos.ClienteDTO;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService service;

    @GET
    @Operation(summary = "Lista todos os clientes")
    public List<Cliente> findAll() {
        System.out.println(service.findAll());
        return service.findAll();
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Busca por id")
    public Optional<Cliente> findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    @Operation(summary = "Salva um cliente")
    public Response saveCliente(@Valid ClienteDTO dto){

        var criado = service.saveCliente(dto);
        return Response.created(URI.create(String.format("/clientes/%s",criado.nome())))
                .entity(criado).build();
    }

}
