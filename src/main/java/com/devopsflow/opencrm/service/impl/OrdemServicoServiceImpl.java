package com.devopsflow.opencrm.service.impl;

import com.devopsflow.opencrm.model.OrdemServico;
import com.devopsflow.opencrm.model.OrdemServicoRepository;
import com.devopsflow.opencrm.service.OrdemServicoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class OrdemServicoServiceImpl implements OrdemServicoService {
    private final OrdemServicoRepository repository;

    public OrdemServicoServiceImpl(OrdemServicoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<OrdemServico> listAll() {
        return repository.listAll();
    }

    @Override
    @Transactional
    public void create(OrdemServico ordemServico) {
        repository.persist(ordemServico);
    }

    @Override
    public OrdemServico get(Long id) {
        return repository.findByIdOptional(id)
                .orElseThrow(() -> new WebApplicationException("Ordem de serviço não encontrada", Response.Status.NOT_FOUND));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new WebApplicationException("Ordem de serviço não encontrada", Response.Status.NOT_FOUND);
        }
    }
} 