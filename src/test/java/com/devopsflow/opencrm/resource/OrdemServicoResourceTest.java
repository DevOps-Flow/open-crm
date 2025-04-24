package com.devopsflow.opencrm.resource;

import com.devopsflow.opencrm.model.Cliente;
import com.devopsflow.opencrm.model.OrdemServico;
import com.devopsflow.opencrm.service.OrdemServicoService;
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
import static org.mockito.Mockito.*;

@QuarkusTest
class OrdemServicoResourceTest {

    @InjectMock
    OrdemServicoService service;

    private OrdemServico ordemServico;
    private Long ordemServicoId;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        ordemServicoId = 1L;
        cliente = new Cliente();
        cliente.setId(UUID.randomUUID());

        ordemServico = new OrdemServico();
        ordemServico.id = ordemServicoId;
        ordemServico.cliente = cliente;
        ordemServico.descricao = "Manutenção de Computador";
        ordemServico.dataAbertura = Timestamp.from(Instant.now());
    }

    @Test
    void listAll_DeveRetornarListaDeOrdensServico() {
        when(service.listAll()).thenReturn(Arrays.asList(ordemServico));

        given()
            .when()
                .get("/ordens-servico")
            .then()
                .statusCode(200)
                .body(
                    "size()", is(1),
                    "[0].id", is(ordemServicoId.intValue()),
                    "[0].descricao", is("Manutenção de Computador")
                );

        verify(service).listAll();
    }

    @Test
    void create_DeveCriarOrdemServico() {
        doNothing().when(service).create(any(OrdemServico.class));

        given()
            .contentType("application/json")
            .body(ordemServico)
        .when()
            .post("/ordens-servico")
        .then()
            .statusCode(201)
            .body(
                "id", notNullValue(),
                "descricao", is("Manutenção de Computador")
            );

        verify(service).create(any(OrdemServico.class));
    }

    @Test
    void get_QuandoOrdemServicoExiste_DeveRetornarOrdemServico() {
        when(service.get(ordemServicoId)).thenReturn(ordemServico);

        given()
            .pathParam("id", ordemServicoId)
        .when()
            .get("/ordens-servico/{id}")
        .then()
            .statusCode(200)
            .body(
                "id", is(ordemServicoId.intValue()),
                "descricao", is("Manutenção de Computador")
            );

        verify(service).get(ordemServicoId);
    }

    @Test
    void get_QuandoOrdemServicoNaoExiste_DeveRetornar404() {
        when(service.get(ordemServicoId))
            .thenThrow(new WebApplicationException("Ordem de serviço não encontrada", Response.Status.NOT_FOUND));

        given()
            .pathParam("id", ordemServicoId)
        .when()
            .get("/ordens-servico/{id}")
        .then()
            .statusCode(404);

        verify(service).get(ordemServicoId);
    }

    @Test
    void delete_QuandoOrdemServicoExiste_DeveRetornar204() {
        doNothing().when(service).delete(ordemServicoId);

        given()
            .pathParam("id", ordemServicoId)
        .when()
            .delete("/ordens-servico/{id}")
        .then()
            .statusCode(204);

        verify(service).delete(ordemServicoId);
    }

    @Test
    void delete_QuandoOrdemServicoNaoExiste_DeveRetornar404() {
        doThrow(new WebApplicationException("Ordem de serviço não encontrada", Response.Status.NOT_FOUND))
            .when(service).delete(ordemServicoId);

        given()
            .pathParam("id", ordemServicoId)
        .when()
            .delete("/ordens-servico/{id}")
        .then()
            .statusCode(404);

        verify(service).delete(ordemServicoId);
    }
} 