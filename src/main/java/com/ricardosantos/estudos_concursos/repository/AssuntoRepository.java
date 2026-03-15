package com.ricardosantos.estudos_concursos.repository;

import com.ricardosantos.estudos_concursos.model.Assunto;
import com.ricardosantos.estudos_concursos.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssuntoRepository extends JpaRepository<Assunto, Long> {

    // Busca todos os assuntos de uma matéria específica
    List<Assunto> findByMateria(Materia materia);

    // Busca assuntos já estudados de uma matéria
    List<Assunto> findByMateriaAndEstudado(Materia materia, boolean estudado);

    // Conta quantos assuntos estudados existem em uma matéria
    long countByMateriaAndEstudado(Materia materia, boolean estudado);
}