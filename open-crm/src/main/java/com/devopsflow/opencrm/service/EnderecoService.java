package com.devopsflow.opencrm.service;

import com.devopsflow.opencrm.model.Endereco;

import java.util.List;

public interface EnderecoService {
    List<Endereco> listAll();
    
    void create(Endereco endereco);
    
    Endereco get(Long id);
    
    void delete(Long id);
}
