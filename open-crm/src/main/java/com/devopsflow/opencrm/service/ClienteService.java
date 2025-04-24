package com.devopsflow.opencrm.service;

import com.devopsflow.opencrm.model.Cliente;

import java.util.List;
import java.util.UUID;

public interface ClienteService {
    List<Cliente> listAll();
    
    void create(Cliente cliente);
    
    Cliente get(UUID id);
    
    void delete(UUID id);
    
    void update(UUID id, Cliente cliente);
}
