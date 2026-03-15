package com.ricardosantos.estudos_concursos.service;

import com.ricardosantos.estudos_concursos.model.Materia;
import com.ricardosantos.estudos_concursos.model.enums.Prioridade;
import com.ricardosantos.estudos_concursos.repository.MateriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MateriaService {

    private final MateriaRepository materiaRepository;

    public List<Materia> listarTodas() {
        return materiaRepository.findAllByOrderByPesoDesc();
    }

    public Materia buscarPorId(Long id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matéria não encontrada: " + id));
    }

    public Materia salvar(Materia materia) {
        return materiaRepository.save(materia);
    }

    public void deletar(Long id) {
        materiaRepository.deleteById(id);
    }

    public long contarTotal() {
        return materiaRepository.count();
    }
}