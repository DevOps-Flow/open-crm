package com.devopsflow.opencrm.service.impl;

import com.devopsflow.opencrm.model.Endereco;
import com.devopsflow.opencrm.model.EnderecoRepository;
import com.devopsflow.opencrm.service.EnderecoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {
    private final EnderecoRepository repository;

    public EnderecoServiceImpl(EnderecoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Endereco> listAll() {
        return repository.listAll();
    }

    @Override
    @Transactional
    public void create(Endereco endereco) {
        repository.persist(endereco);
    }

    @Override
    public Endereco get(Long id) {
        return repository.findByIdOptional(id)
                .orElseThrow(() -> new WebApplicationException("Endereço não encontrado", Response.Status.NOT_FOUND));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new WebApplicationException("Endereço não encontrado", Response.Status.NOT_FOUND);
        }
    }
} 