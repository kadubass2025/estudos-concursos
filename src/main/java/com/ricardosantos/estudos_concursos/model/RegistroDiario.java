package com.ricardosantos.estudos_concursos.model;

import com.ricardosantos.estudos_concursos.model.enums.TipoEstudo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    private String assunto;

    @Enumerated(EnumType.STRING)
    private TipoEstudo tipo;

    private Integer tempoPlanejado;

    private Integer tempoReal;

    private Integer questoesResolvidas;

    private Integer acertos;

    @Column(length = 500)
    private String observacoes;

    // Calcula o percentual de acerto automaticamente
    public double getPercentualAcerto() {
        if (questoesResolvidas == null || questoesResolvidas == 0) return 0;
        return (acertos * 100.0) / questoesResolvidas;
    }
}