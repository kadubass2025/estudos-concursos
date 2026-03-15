package com.ricardosantos.estudos_concursos.model;

import com.ricardosantos.estudos_concursos.model.enums.StatusRevisao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Revisao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    private String assunto;

    private LocalDate dataEstudo;

    private LocalDate dataRevisao1;  // +7 dias

    private LocalDate dataRevisao2;  // +15 dias

    private LocalDate dataRevisao3;  // +30 dias

    @Enumerated(EnumType.STRING)
    private StatusRevisao statusRev1 = StatusRevisao.PENDENTE;

    @Enumerated(EnumType.STRING)
    private StatusRevisao statusRev2 = StatusRevisao.PENDENTE;

    @Enumerated(EnumType.STRING)
    private StatusRevisao statusRev3 = StatusRevisao.PENDENTE;

    @PrePersist
    public void calcularDatasRevisao() {
        this.dataRevisao1 = dataEstudo.plusDays(7);
        this.dataRevisao2 = dataEstudo.plusDays(15);
        this.dataRevisao3 = dataEstudo.plusDays(30);
    }

    public boolean isAtrasada(LocalDate dataRevisao, StatusRevisao status) {
        return status == StatusRevisao.PENDENTE
                && LocalDate.now().isAfter(dataRevisao);
    }
}