package com.devopsflow.opencrm.service.impl;

import com.devopsflow.opencrm.model.Cliente;
import com.devopsflow.opencrm.model.OrdemServico;
import com.devopsflow.opencrm.model.OrdemServicoRepository;
import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdemServicoServiceImplTest {

    @Mock
    private OrdemServicoRepository repository;

    @InjectMocks
    private OrdemServicoServiceImpl service;

    private OrdemServico ordemServico;
    private Long ordemServicoId;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        ordemServicoId = 1L;
        cliente = new Cliente();
        cliente.id = UUID.randomUUID();

        ordemServico = new OrdemServico();
        ordemServico.id = ordemServicoId;
        ordemServico.cliente = cliente;
        ordemServico.descricao = "Manutenção de Computador";
        ordemServico.dataAbertura = Timestamp.from(Instant.now());
    }

    @Test
    void listAll_DeveRetornarListaDeOrdensServico() {
        List<OrdemServico> ordensServico = Arrays.asList(ordemServico, new OrdemServico());
        when(repository.listAll()).thenReturn(ordensServico);

        List<OrdemServico> resultado = service.listAll();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository).listAll();
    }

    @Test
    void create_DevePersistirOrdemServico() {
        service.create(ordemServico);

        verify(repository).persist(ordemServico);
    }

    @Test
    void get_QuandoOrdemServicoExiste_DeveRetornarOrdemServico() {
        when(repository.findByIdOptional(ordemServicoId)).thenReturn(Optional.of(ordemServico));

        OrdemServico resultado = service.get(ordemServicoId);

        assertNotNull(resultado);
        assertEquals(ordemServicoId, resultado.id);
        assertEquals("Manutenção de Computador", resultado.descricao);
        assertNotNull(resultado.dataAbertura);
        verify(repository).findByIdOptional(ordemServicoId);
    }

    @Test
    void get_QuandoOrdemServicoNaoExiste_DeveLancarExcecao() {
        when(repository.findByIdOptional(ordemServicoId)).thenReturn(Optional.empty());

        WebApplicationException exception = assertThrows(
            WebApplicationException.class,
            () -> service.get(ordemServicoId)
        );

        assertEquals(404, exception.getResponse().getStatus());
        assertEquals("Ordem de serviço não encontrada", exception.getMessage());
        verify(repository).findByIdOptional(ordemServicoId);
    }

    @Test
    void delete_QuandoOrdemServicoExiste_DeveDeletarOrdemServico() {
        when(repository.deleteById(ordemServicoId)).thenReturn(true);

        service.delete(ordemServicoId);

        verify(repository).deleteById(ordemServicoId);
    }

    @Test
    void delete_QuandoOrdemServicoNaoExiste_DeveLancarExcecao() {
        when(repository.deleteById(ordemServicoId)).thenReturn(false);

        WebApplicationException exception = assertThrows(
            WebApplicationException.class,
            () -> service.delete(ordemServicoId)
        );

        assertEquals(404, exception.getResponse().getStatus());
        assertEquals("Ordem de serviço não encontrada", exception.getMessage());
        verify(repository).deleteById(ordemServicoId);
    }
} 