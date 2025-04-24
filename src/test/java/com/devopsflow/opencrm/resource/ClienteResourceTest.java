package com.devopsflow.opencrm.resource;

import com.devopsflow.opencrm.model.Cliente;
import com.devopsflow.opencrm.service.ClienteService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@QuarkusTest
class ClienteResourceTest {

    @InjectMock
    ClienteService service;

    private Cliente cliente;
    private UUID clienteId;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");
        cliente.setTelefone("11999999999");
        cliente.dataCadastro = Timestamp.from(Instant.now());
    }

    @Test
    void listAll_DeveRetornarListaDeClientes() {
        when(service.listAll()).thenReturn(Arrays.asList(cliente));

        given()
            .when()
                .get("/clientes")
            .then()
                .statusCode(200)
                .body(
                    "size()", is(1),
                    "[0].id", is(clienteId.toString()),
                    "[0].nome", is("João Silva"),
                    "[0].email", is("joao@email.com")
                );

        verify(service).listAll();
    }

    @Test
    void create_DeveCriarCliente() {
        doNothing().when(service).create(any(Cliente.class));

        given()
            .contentType("application/json")
            .body(cliente)
        .when()
            .post("/clientes")
        .then()
            .statusCode(201)
            .body(
                "id", notNullValue(),
                "nome", is("João Silva"),
                "email", is("joao@email.com")
            );

        verify(service).create(any(Cliente.class));
    }

    @Test
    void get_QuandoClienteExiste_DeveRetornarCliente() {
        when(service.get(clienteId)).thenReturn(cliente);

        given()
            .pathParam("id", clienteId)
        .when()
            .get("/clientes/{id}")
        .then()
            .statusCode(200)
            .body(
                "id", is(clienteId.toString()),
                "nome", is("João Silva"),
                "email", is("joao@email.com")
            );

        verify(service).get(clienteId);
    }

    @Test
    void get_QuandoClienteNaoExiste_DeveRetornar404() {
        when(service.get(clienteId))
            .thenThrow(new WebApplicationException("Cliente não encontrado", Response.Status.NOT_FOUND));

        given()
            .pathParam("id", clienteId)
        .when()
            .get("/clientes/{id}")
        .then()
            .statusCode(404);

        verify(service).get(clienteId);
    }

    @Test
    void delete_QuandoClienteExiste_DeveRetornar204() {
        doNothing().when(service).delete(clienteId);

        given()
            .pathParam("id", clienteId)
        .when()
            .delete("/clientes/{id}")
        .then()
            .statusCode(204);

        verify(service).delete(clienteId);
    }

    @Test
    void delete_QuandoClienteNaoExiste_DeveRetornar404() {
        doThrow(new WebApplicationException("Cliente não encontrado", Response.Status.NOT_FOUND))
            .when(service).delete(clienteId);

        given()
            .pathParam("id", clienteId)
        .when()
            .delete("/clientes/{id}")
        .then()
            .statusCode(404);

        verify(service).delete(clienteId);
    }

    @Test
    void update_QuandoClienteExiste_DeveAtualizarCliente() {
        doNothing().when(service).update(eq(clienteId), any(Cliente.class));

        given()
            .contentType("application/json")
            .pathParam("id", clienteId)
            .body(cliente)
        .when()
            .put("/clientes/{id}")
        .then()
            .statusCode(200)
            .body(
                "id", is(clienteId.toString()),
                "nome", is("João Silva"),
                "email", is("joao@email.com")
            );

        verify(service).update(eq(clienteId), any(Cliente.class));
    }

    @Test
    void update_QuandoClienteNaoExiste_DeveRetornar404() {
        doThrow(new WebApplicationException("Cliente não encontrado", Response.Status.NOT_FOUND))
            .when(service).update(eq(clienteId), any(Cliente.class));

        given()
            .contentType("application/json")
            .pathParam("id", clienteId)
            .body(cliente)
        .when()
            .put("/clientes/{id}")
        .then()
            .statusCode(404);

        verify(service).update(eq(clienteId), any(Cliente.class));
    }
} 