package com.devopsflow.opencrm.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    @Column(nullable = false, length = 100)
    public String nome;

    @Column(nullable = false, length = 100, unique = true)
    public String email;

    @Column(length = 20)
    public String telefone;

    @Column(name = "data_cadastro")
    public java.sql.Timestamp dataCadastro = new Timestamp(System.currentTimeMillis());

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Timestamp getDataCadastro() {
        return dataCadastro;
    }
}
