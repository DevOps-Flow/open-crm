package com.devopsflow.opencrm.service.impl;

import com.devopsflow.opencrm.model.Cliente;
import com.devopsflow.opencrm.model.ClienteRepository;
import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteServiceImpl service;

    private Cliente cliente;
    private UUID clienteId;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");
    }

    @Test
    void listAll_DeveRetornarListaDeClientes() {
        List<Cliente> clientes = Arrays.asList(cliente, new Cliente());
        when(repository.listAll()).thenReturn(clientes);

        List<Cliente> resultado = service.listAll();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository).listAll();
    }

    @Test
    void create_DevePersistirCliente() {
        service.create(cliente);

        verify(repository).persist(cliente);
    }

    @Test
    void get_QuandoClienteExiste_DeveRetornarCliente() {
        when(repository.findByIdOptional(clienteId)).thenReturn(Optional.of(cliente));

        Cliente resultado = service.get(clienteId);

        assertNotNull(resultado);
        assertEquals(clienteId, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        verify(repository).findByIdOptional(clienteId);
    }

    @Test
    void get_QuandoClienteNaoExiste_DeveLancarExcecao() {
        when(repository.findByIdOptional(clienteId)).thenReturn(Optional.empty());

        WebApplicationException exception = assertThrows(
            WebApplicationException.class,
            () -> service.get(clienteId)
        );

        assertEquals(404, exception.getResponse().getStatus());
        assertEquals("Cliente não encontrado", exception.getMessage());
        verify(repository).findByIdOptional(clienteId);
    }

    @Test
    void delete_QuandoClienteExiste_DeveDeletarCliente() {
        when(repository.deleteById(clienteId)).thenReturn(true);

        service.delete(clienteId);

        verify(repository).deleteById(clienteId);
    }

    @Test
    void delete_QuandoClienteNaoExiste_DeveLancarExcecao() {
        when(repository.deleteById(clienteId)).thenReturn(false);

        WebApplicationException exception = assertThrows(
            WebApplicationException.class,
            () -> service.delete(clienteId)
        );

        assertEquals(404, exception.getResponse().getStatus());
        assertEquals("Cliente não encontrado", exception.getMessage());
        verify(repository).deleteById(clienteId);
    }

    @Test
    void update_QuandoClienteExiste_DeveAtualizarCliente() {
        when(repository.update(eq(clienteId), any(Cliente.class))).thenReturn(true);

        service.update(clienteId, cliente);

        verify(repository).update(clienteId, cliente);
    }

    @Test
    void update_QuandoClienteNaoExiste_DeveLancarExcecao() {
        when(repository.update(eq(clienteId), any(Cliente.class))).thenReturn(false);

        WebApplicationException exception = assertThrows(
            WebApplicationException.class,
            () -> service.update(clienteId, cliente)
        );

        assertEquals(404, exception.getResponse().getStatus());
        assertEquals("Cliente não encontrado", exception.getMessage());
        verify(repository).update(clienteId, cliente);
    }
} 