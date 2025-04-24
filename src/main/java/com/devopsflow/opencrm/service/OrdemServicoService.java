package com.devopsflow.opencrm.service;

import com.devopsflow.opencrm.model.OrdemServico;

import java.util.List;

public interface OrdemServicoService {
    List<OrdemServico> listAll();
    
    void create(OrdemServico ordemServico);
    
    OrdemServico get(Long id);
    
    void delete(Long id);
}
