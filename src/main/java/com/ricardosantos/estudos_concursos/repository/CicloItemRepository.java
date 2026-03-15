package com.ricardosantos.estudos_concursos.repository;

import com.ricardosantos.estudos_concursos.model.CicloItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CicloItemRepository extends JpaRepository<CicloItem, Long> {

    // Busca todos os itens do ciclo ordenados pela ordem definida
    List<CicloItem> findAllByOrderByOrdemAsc();
}
