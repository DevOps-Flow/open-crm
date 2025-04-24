package com.devopsflow.opencrm.service.impl;

import com.devopsflow.opencrm.model.Endereco;
import com.devopsflow.opencrm.model.EnderecoRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceImplTest {

    @Mock
    private EnderecoRepository repository;

    @InjectMocks
    private EnderecoServiceImpl service;

    private Endereco endereco;
    private Long enderecoId;

    @BeforeEach
    void setUp() {
        enderecoId = 1L;
        endereco = new Endereco();
        endereco.setId(enderecoId);
        endereco.setRua("Rua das Flores");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
        endereco.setCep("01234-567");
    }

    @Test
    void listAll_DeveRetornarListaDeEnderecos() {
        List<Endereco> enderecos = Arrays.asList(endereco, new Endereco());
        when(repository.listAll()).thenReturn(enderecos);

        List<Endereco> resultado = service.listAll();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository).listAll();
    }

    @Test
    void create_DevePersistirEndereco() {
        service.create(endereco);

        verify(repository).persist(endereco);
    }

    @Test
    void get_QuandoEnderecoExiste_DeveRetornarEndereco() {
        when(repository.findByIdOptional(enderecoId)).thenReturn(Optional.of(endereco));

        Endereco resultado = service.get(enderecoId);

        assertNotNull(resultado);
        assertEquals(enderecoId, resultado.getId());
        assertEquals("Rua das Flores", resultado.getRua());
        assertEquals("123", resultado.getNumero());
        verify(repository).findByIdOptional(enderecoId);
    }

    @Test
    void get_QuandoEnderecoNaoExiste_DeveLancarExcecao() {
        when(repository.findByIdOptional(enderecoId)).thenReturn(Optional.empty());

        WebApplicationException exception = assertThrows(
            WebApplicationException.class,
            () -> service.get(enderecoId)
        );

        assertEquals(404, exception.getResponse().getStatus());
        assertEquals("Endereço não encontrado", exception.getMessage());
        verify(repository).findByIdOptional(enderecoId);
    }

    @Test
    void delete_QuandoEnderecoExiste_DeveDeletarEndereco() {
        when(repository.deleteById(enderecoId)).thenReturn(true);

        service.delete(enderecoId);

        verify(repository).deleteById(enderecoId);
    }

    @Test
    void delete_QuandoEnderecoNaoExiste_DeveLancarExcecao() {
        when(repository.deleteById(enderecoId)).thenReturn(false);

        WebApplicationException exception = assertThrows(
            WebApplicationException.class,
            () -> service.delete(enderecoId)
        );

        assertEquals(404, exception.getResponse().getStatus());
        assertEquals("Endereço não encontrado", exception.getMessage());
        verify(repository).deleteById(enderecoId);
    }
} 