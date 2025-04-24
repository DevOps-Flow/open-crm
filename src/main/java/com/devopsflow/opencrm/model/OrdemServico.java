package com.devopsflow.opencrm.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ordens_servico")
public class OrdemServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    public Cliente cliente;

    @Column(nullable = false, length = 200)
    public String descricao;

    @Column(nullable = false)
    public java.sql.Timestamp dataAbertura;

    @Column
    public java.sql.Timestamp dataConclusao;
}
