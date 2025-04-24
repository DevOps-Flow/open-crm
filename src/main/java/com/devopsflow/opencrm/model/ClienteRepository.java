package com.devopsflow.opencrm.model;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;


@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public Optional<Cliente> findByIdOptional(UUID id) {
        return find("id", id).firstResultOptional();
    }

    public boolean deleteById(UUID id) {
        return delete("id", id) > 0;
    }

    public boolean update(UUID id, Cliente cliente) {
        return update("nome = ?1, email = ?2, telefone = ?3 where id = ?4", cliente.nome, cliente.email, cliente.telefone, id) > 0;
    }
}
