package com.ricardosantos.estudos_concursos.service;

import com.ricardosantos.estudos_concursos.model.Revisao;
import com.ricardosantos.estudos_concursos.model.enums.StatusRevisao;
import com.ricardosantos.estudos_concursos.repository.RevisaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RevisaoService {

    private final RevisaoRepository revisaoRepository;

    // Retorna todas as revisões
    public List<Revisao> listarTodas() {
        return revisaoRepository.findAllByOrderByDataEstudoDesc();
    }

    // Salva uma revisão e calcula as datas automaticamente
    public Revisao salvar(Revisao revisao) {
        return revisaoRepository.save(revisao);
    }

    // Deleta uma revisão pelo id
    public void deletar(Long id) {
        revisaoRepository.deleteById(id);
    }

    // Busca uma revisão pelo id
    public Revisao buscarPorId(Long id) {
        return revisaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Revisão não encontrada: " + id));
    }

    // Retorna revisões pendentes até hoje (inclui atrasadas)
    public List<Revisao> listarPendentesHoje() {
        return revisaoRepository.findRevisoesPendentesAteHoje(
                StatusRevisao.PENDENTE,
                LocalDate.now()
        );
    }

    // Retorna revisões exatamente para hoje
    public List<Revisao> listarDeHoje() {
        return revisaoRepository.findRevisoesDeHoje(
                StatusRevisao.PENDENTE,
                LocalDate.now()
        );
    }

    // Marca uma revisão específica como concluída
    public Revisao concluirRevisao(Long id, int numeroRevisao) {
        Revisao revisao = buscarPorId(id);

        switch (numeroRevisao) {
            case 1 -> revisao.setStatusRev1(StatusRevisao.CONCLUIDA);
            case 2 -> revisao.setStatusRev2(StatusRevisao.CONCLUIDA);
            case 3 -> revisao.setStatusRev3(StatusRevisao.CONCLUIDA);
        }

        return revisaoRepository.save(revisao);
    }

    // Conta quantas revisões estão pendentes hoje
    public long contarPendentesHoje() {
        return listarPendentesHoje().size();
    }
}