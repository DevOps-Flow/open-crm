package com.devopsflow.opencrm.service;

import com.devopsflow.opencrm.model.Endereco;
import com.devopsflow.opencrm.model.EnderecoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class EnderecoService {
    private final EnderecoRepository repository;

    public EnderecoService(EnderecoRepository repository) {
        this.repository = repository;
    }

    public List<Endereco> listAll() {
        return repository.listAll();
    }

    public void create(Endereco endereco) {
        repository.persist(endereco);
    }

    public Endereco get(Long id) {
        return repository.findByIdOptional(id).orElseThrow(() -> new WebApplicationException("Endereço não encontrado", Response.Status.NOT_FOUND));
    }

    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new WebApplicationException("Endereço não encontrado", Response.Status.NOT_FOUND);
        }
    }
}
