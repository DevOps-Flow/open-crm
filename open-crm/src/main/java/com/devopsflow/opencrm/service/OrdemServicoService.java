package com.devopsflow.opencrm.service;

import com.devopsflow.opencrm.model.OrdemServico;
import com.devopsflow.opencrm.model.OrdemServicoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class OrdemServicoService {
    private final OrdemServicoRepository repository;

    public OrdemServicoService(OrdemServicoRepository repository) {
        this.repository = repository;
    }

    public List<OrdemServico> listAll() {
        return repository.listAll();
    }

    public void create(OrdemServico ordemServico) {
        repository.persist(ordemServico);
    }

    public OrdemServico get(Long id) {
        return repository.findByIdOptional(id).orElseThrow(() -> new WebApplicationException("Ordem de serviço não encontrada", Response.Status.NOT_FOUND));
    }

    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new WebApplicationException("Ordem de serviço não encontrada", Response.Status.NOT_FOUND);
        }
    }
}
