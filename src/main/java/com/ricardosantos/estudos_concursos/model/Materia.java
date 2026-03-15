package com.ricardosantos.estudos_concursos.model;

import com.ricardosantos.estudos_concursos.model.enums.Prioridade;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da matéria é obrigatório")
    private String nome;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    private Integer peso;

    private Integer totalAssuntos;

    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Assunto> assuntos = new ArrayList<>();

    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RegistroDiario> registros = new ArrayList<>();

    public long getAssuntosEstudados() {
        return assuntos.stream()
                .filter(Assunto::isEstudado)
                .count();
    }

    public double getProgresso() {
        if (totalAssuntos == null || totalAssuntos == 0) return 0;
        return (getAssuntosEstudados() * 100.0) / totalAssuntos;
    }
}