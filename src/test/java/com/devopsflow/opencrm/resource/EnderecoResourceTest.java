package com.devopsflow.opencrm.resource;

import com.devopsflow.opencrm.model.Cliente;
import com.devopsflow.opencrm.model.Endereco;
import com.devopsflow.opencrm.service.EnderecoService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
class EnderecoResourceTest {

    @InjectMock
    EnderecoService service;

    private Endereco endereco;
    private Long enderecoId;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        enderecoId = 1L;
        cliente = new Cliente();
        cliente.setId(UUID.randomUUID());

        endereco = new Endereco();
        endereco.setId(enderecoId);
        endereco.setRua("Rua das Flores");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
        endereco.setCep("01234-567");
        endereco.setCliente(cliente);
    }

    @Test
    void listAll_DeveRetornarListaDeEnderecos() {
        when(service.listAll()).thenReturn(Arrays.asList(endereco));

        given()
            .when()
                .get("/enderecos")
            .then()
                .statusCode(200)
                .body(
                    "size()", is(1),
                    "[0].id", is(enderecoId.intValue()),
                    "[0].rua", is("Rua das Flores"),
                    "[0].numero", is("123")
                );

        verify(service).listAll();
    }

    @Test
    void create_DeveCriarEndereco() {
        doNothing().when(service).create(any(Endereco.class));

        given()
            .contentType("application/json")
            .body(endereco)
        .when()
            .post("/enderecos")
        .then()
            .statusCode(201)
            .body(
                "id", notNullValue(),
                "rua", is("Rua das Flores"),
                "numero", is("123")
            );

        verify(service).create(any(Endereco.class));
    }

    @Test
    void get_QuandoEnderecoExiste_DeveRetornarEndereco() {
        when(service.get(enderecoId)).thenReturn(endereco);

        given()
            .pathParam("id", enderecoId)
        .when()
            .get("/enderecos/{id}")
        .then()
            .statusCode(200)
            .body(
                "id", is(enderecoId.intValue()),
                "rua", is("Rua das Flores"),
                "numero", is("123")
            );

        verify(service).get(enderecoId);
    }

    @Test
    void get_QuandoEnderecoNaoExiste_DeveRetornar404() {
        when(service.get(enderecoId))
            .thenThrow(new WebApplicationException("Endereço não encontrado", Response.Status.NOT_FOUND));

        given()
            .pathParam("id", enderecoId)
        .when()
            .get("/enderecos/{id}")
        .then()
            .statusCode(404);

        verify(service).get(enderecoId);
    }

    @Test
    void delete_QuandoEnderecoExiste_DeveRetornar204() {
        doNothing().when(service).delete(enderecoId);

        given()
            .pathParam("id", enderecoId)
        .when()
            .delete("/enderecos/{id}")
        .then()
            .statusCode(204);

        verify(service).delete(enderecoId);
    }

    @Test
    void delete_QuandoEnderecoNaoExiste_DeveRetornar404() {
        doThrow(new WebApplicationException("Endereço não encontrado", Response.Status.NOT_FOUND))
            .when(service).delete(enderecoId);

        given()
            .pathParam("id", enderecoId)
        .when()
            .delete("/enderecos/{id}")
        .then()
            .statusCode(404);

        verify(service).delete(enderecoId);
    }
} 