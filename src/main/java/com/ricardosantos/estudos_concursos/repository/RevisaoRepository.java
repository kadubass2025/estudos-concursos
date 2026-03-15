package com.ricardosantos.estudos_concursos.repository;

import com.ricardosantos.estudos_concursos.model.Revisao;
import com.ricardosantos.estudos_concursos.model.enums.StatusRevisao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RevisaoRepository extends JpaRepository<Revisao, Long> {

    // Busca todas as revisões ordenadas por data de estudo
    List<Revisao> findAllByOrderByDataEstudoDesc();

    // Busca revisões pendentes cuja data já passou (atrasadas)
    @Query("""
        SELECT r FROM Revisao r WHERE
        (r.statusRev1 = :status AND r.dataRevisao1 <= :hoje) OR
        (r.statusRev2 = :status AND r.dataRevisao2 <= :hoje) OR
        (r.statusRev3 = :status AND r.dataRevisao3 <= :hoje)
        """)
    List<Revisao> findRevisoesPendentesAteHoje(StatusRevisao status, LocalDate hoje);

    // Busca revisões com data exatamente hoje
    @Query("""
        SELECT r FROM Revisao r WHERE
        (r.statusRev1 = :status AND r.dataRevisao1 = :hoje) OR
        (r.statusRev2 = :status AND r.dataRevisao2 = :hoje) OR
        (r.statusRev3 = :status AND r.dataRevisao3 = :hoje)
        """)
    List<Revisao> findRevisoesDeHoje(StatusRevisao status, LocalDate hoje);
}