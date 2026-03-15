package com.ricardosantos.estudos_concursos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private boolean estudado = false;

    private LocalDate dataEstudo;

    @Enumerated(EnumType.STRING)
    private com.ricardosantos.estudos_concursos.model.enums.NivelDificuldade nivelDificuldade;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;
}