package com.onload;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.onload.domain.services.ClienteService;


import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class ClienteServiceTest {
    
    @Inject
    ClienteService clienteService;


    @Test
    @DisplayName("teste de retorno da lista de clientes")
    void findClientes()
    {
        var res  = clienteService.findAll();
        assertTrue(!res.isEmpty());

    }
}
