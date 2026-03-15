package com.ricardosantos.estudos_concursos.model;

import com.ricardosantos.estudos_concursos.model.enums.Prioridade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CicloItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    private Integer tempoPlanejado;

    private Integer diasNoCiclo;

    private Integer ordem;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    private LocalDate dataInicioCiclo;

    // Calcula a próxima sessão com base na ordem e dias do ciclo
    public LocalDate getProximaSessao() {
        if (dataInicioCiclo == null) return null;
        return dataInicioCiclo.plusDays(ordem - 1L);
    }
}
