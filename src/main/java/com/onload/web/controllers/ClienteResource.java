package com.onload.web.controllers;

import java.util.List;

import com.onload.domain.models.Cliente;
import com.onload.domain.services.ClienteService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService service;


    @GET
    public List<Cliente> findAll()
    {
        System.out.println(service.findAll());
        return service.findAll();
    }

}
