package com.dev.pontoeletronicoapi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PontoBatida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Usuario usuario;

    private LocalDateTime dataHora;

    private String tipo;

    private String observacao;
}

