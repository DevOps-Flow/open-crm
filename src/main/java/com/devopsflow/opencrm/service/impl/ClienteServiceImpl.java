package com.devopsflow.opencrm.service.impl;

import com.devopsflow.opencrm.model.Cliente;
import com.devopsflow.opencrm.model.ClienteRepository;
import com.devopsflow.opencrm.service.ClienteService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository repository;

    public ClienteServiceImpl(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Cliente> listAll() {
        return repository.listAll();
    }

    @Override
    @Transactional
    public void create(Cliente cliente) {
        repository.persist(cliente);
    }

    @Override
    public Cliente get(UUID id) {
        return repository.findByIdOptional(id)
                .orElseThrow(() -> new WebApplicationException("Cliente não encontrado", Response.Status.NOT_FOUND));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repository.deleteById(id)) {
            throw new WebApplicationException("Cliente não encontrado", Response.Status.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void update(UUID id, Cliente cliente) {
        if (!repository.update(id, cliente)) {
            throw new WebApplicationException("Cliente não encontrado", Response.Status.NOT_FOUND);
        }
    }
} 