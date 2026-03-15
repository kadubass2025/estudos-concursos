package com.ricardosantos.estudos_concursos.repository;

import com.ricardosantos.estudos_concursos.model.Materia;
import com.ricardosantos.estudos_concursos.model.RegistroDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegistroDiarioRepository extends JpaRepository<RegistroDiario, Long> {

    // Busca registros de uma matéria específica
    List<RegistroDiario> findByMateria(Materia materia);

    // Busca registros por data
    List<RegistroDiario> findByData(LocalDate data);

    // Busca registros entre duas datas (para gráfico semanal)
    List<RegistroDiario> findByDataBetween(LocalDate inicio, LocalDate fim);

    // Busca todos os registros ordenados do mais recente para o mais antigo
    List<RegistroDiario> findAllByOrderByDataDesc();

    // Busca registros de uma matéria ordenados por data
    List<RegistroDiario> findByMateriaOrderByDataDesc(Materia materia);

    // Soma total de minutos estudados de uma matéria
    @Query("SELECT COALESCE(SUM(r.tempoReal), 0) FROM RegistroDiario r WHERE r.materia = :materia")
    Integer sumTempoRealByMateria(Materia materia);

    // Soma total de questões resolvidas de uma matéria
    @Query("SELECT COALESCE(SUM(r.questoesResolvidas), 0) FROM RegistroDiario r WHERE r.materia = :materia")
    Integer sumQuestoesResolvidasByMateria(Materia materia);

    // Soma total de acertos de uma matéria
    @Query("SELECT COALESCE(SUM(r.acertos), 0) FROM RegistroDiario r WHERE r.materia = :materia")
    Integer sumAcertosByMateria(Materia materia);
}