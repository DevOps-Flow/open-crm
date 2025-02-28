package com.devopsflow.opencrm.service;

import com.devopsflow.opencrm.model.Cliente;
import com.devopsflow.opencrm.model.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listAll() {
        return repository.listAll();
    }

    @Transactional
    public void create(Cliente cliente) {
        repository.persist(cliente);
    }

    public Cliente get(UUID id) {
        return repository.findByIdOptional(id).orElseThrow(() -> new WebApplicationException("Cliente não encontrado", Response.Status.NOT_FOUND));
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.deleteById(id)) {
            throw new WebApplicationException("Cliente não encontrado", Response.Status.NOT_FOUND);
        }
    }

    @Transactional
    public void update(UUID id, Cliente cliente) {
        if (!repository.update(id, cliente)) {
            throw new WebApplicationException("Cliente não encontrado", Response.Status.NOT_FOUND);
        }
    }
}
