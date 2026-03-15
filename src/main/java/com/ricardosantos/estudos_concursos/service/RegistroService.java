package com.ricardosantos.estudos_concursos.service;

import com.ricardosantos.estudos_concursos.model.RegistroDiario;
import com.ricardosantos.estudos_concursos.repository.RegistroDiarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistroService {

    private final RegistroDiarioRepository registroRepository;

    // Retorna todos os registros do mais recente para o mais antigo
    public List<RegistroDiario> listarTodos() {
        return registroRepository.findAllByOrderByDataDesc();
    }

    // Retorna os últimos N registros
    public List<RegistroDiario> listarUltimos(int quantidade) {
        return registroRepository.findAllByOrderByDataDesc()
                .stream()
                .limit(quantidade)
                .toList();
    }

    // Busca registros entre duas datas
    public List<RegistroDiario> listarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return registroRepository.findByDataBetween(inicio, fim);
    }

    // Salva um novo registro diário
    public RegistroDiario salvar(RegistroDiario registro) {
        // Se a data não foi informada, usa a data de hoje
        if (registro.getData() == null) {
            registro.setData(LocalDate.now());
        }
        return registroRepository.save(registro);
    }

    // Deleta um registro pelo id
    public void deletar(Long id) {
        registroRepository.deleteById(id);
    }

    // Busca um registro pelo id
    public RegistroDiario buscarPorId(Long id) {
        return registroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado: " + id));
    }

    // Calcula o total de minutos estudados em geral
    public int calcularTotalMinutosEstudados() {
        return registroRepository.findAll()
                .stream()
                .filter(r -> r.getTempoReal() != null)
                .mapToInt(RegistroDiario::getTempoReal)
                .sum();
    }

    // Calcula o total de questões resolvidas em geral
    public int calcularTotalQuestoes() {
        return registroRepository.findAll()
                .stream()
                .filter(r -> r.getQuestoesResolvidas() != null)
                .mapToInt(RegistroDiario::getQuestoesResolvidas)
                .sum();
    }

    // Calcula o total de acertos em geral
    public int calcularTotalAcertos() {
        return registroRepository.findAll()
                .stream()
                .filter(r -> r.getAcertos() != null)
                .mapToInt(RegistroDiario::getAcertos)
                .sum();
    }
}
