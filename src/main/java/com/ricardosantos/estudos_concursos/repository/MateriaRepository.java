package com.ricardosantos.estudos_concursos.repository;

import com.ricardosantos.estudos_concursos.model.Materia;
import com.ricardosantos.estudos_concursos.model.enums.Prioridade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {

    // Busca matérias por prioridade (ex: todas as de prioridade ALTA)
    List<Materia> findByPrioridade(Prioridade prioridade);

    // Busca matérias pelo nome ignorando maiúsculas/minúsculas
    List<Materia> findByNomeContainingIgnoreCase(String nome);

    // Busca matérias ordenadas por peso (da maior para menor)
    List<Materia> findAllByOrderByPesoDesc();
}
