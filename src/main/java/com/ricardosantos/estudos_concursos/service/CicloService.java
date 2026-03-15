package com.ricardosantos.estudos_concursos.service;

import com.ricardosantos.estudos_concursos.model.CicloItem;
import com.ricardosantos.estudos_concursos.repository.CicloItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CicloService {

    private final CicloItemRepository cicloItemRepository;

    // Retorna todos os itens do ciclo ordenados
    public List<CicloItem> listarTodos() {
        return cicloItemRepository.findAllByOrderByOrdemAsc();
    }

    // Salva um item do ciclo
    public CicloItem salvar(CicloItem cicloItem) {
        return cicloItemRepository.save(cicloItem);
    }

    // Deleta um item do ciclo
    public void deletar(Long id) {
        cicloItemRepository.deleteById(id);
    }

    // Busca um item pelo id
    public CicloItem buscarPorId(Long id) {
        return cicloItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item do ciclo não encontrado: " + id));
    }

    // Calcula o total de minutos planejados no ciclo
    public int calcularTotalMinutosCiclo() {
        return cicloItemRepository.findAll()
                .stream()
                .filter(c -> c.getTempoPlanejado() != null)
                .mapToInt(CicloItem::getTempoPlanejado)
                .sum();
    }

    // Calcula a duração total do ciclo em dias
    public int calcularDuracaoCicloEmDias() {
        return cicloItemRepository.findAll()
                .stream()
                .filter(c -> c.getDiasNoCiclo() != null)
                .mapToInt(CicloItem::getDiasNoCiclo)
                .sum();
    }
}