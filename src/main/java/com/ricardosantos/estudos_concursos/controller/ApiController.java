package com.ricardosantos.estudos_concursos.controller;

import com.ricardosantos.estudos_concursos.model.Materia;
import com.ricardosantos.estudos_concursos.model.RegistroDiario;
import com.ricardosantos.estudos_concursos.model.enums.TipoEstudo;
import com.ricardosantos.estudos_concursos.service.MateriaService;
import com.ricardosantos.estudos_concursos.service.RegistroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final MateriaService materiaService;
    private final RegistroService registroService;

    // Retorna apenas id e nome para o cronômetro
    @GetMapping("/materias")
    public List<Map<String, Object>> listarMaterias() {
        return materiaService.listarTodas()
                .stream()
                .map(m -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("id",   m.getId());
                    map.put("nome", m.getNome());
                    return map;
                })
                .collect(Collectors.toList());
    }

    // Salva um registro vindo do cronômetro
    @PostMapping("/registro")
    public ResponseEntity<String> salvarRegistro(
            @RequestBody Map<String, Object> dados) {
        try {
            Long materiaId = Long.parseLong(
                    dados.get("materiaId").toString());
            Materia materia = materiaService.buscarPorId(materiaId);

            RegistroDiario registro = new RegistroDiario();
            registro.setMateria(materia);
            registro.setData(LocalDate.parse(
                    dados.get("data").toString()));
            registro.setAssunto(dados.get("assunto").toString());
            registro.setTipo(TipoEstudo.valueOf(
                    dados.get("tipo").toString()));
            registro.setTempoPlanejado(Integer.parseInt(
                    dados.get("tempoPlanejado").toString()));
            registro.setTempoReal(Integer.parseInt(
                    dados.get("tempoReal").toString()));
            registro.setQuestoesResolvidas(Integer.parseInt(
                    dados.get("questoesResolvidas").toString()));
            registro.setAcertos(Integer.parseInt(
                    dados.get("acertos").toString()));
            registro.setObservacoes(
                    dados.get("observacoes").toString());

            registroService.salvar(registro);
            return ResponseEntity.ok("Salvo com sucesso");

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Erro ao salvar: " + e.getMessage());
        }
    }
}
