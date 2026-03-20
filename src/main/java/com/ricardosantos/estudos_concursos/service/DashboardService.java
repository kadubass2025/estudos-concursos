package com.ricardosantos.estudos_concursos.service;

import com.ricardosantos.estudos_concursos.model.Materia;
import com.ricardosantos.estudos_concursos.model.RegistroDiario;
import com.ricardosantos.estudos_concursos.model.Revisao;
import com.ricardosantos.estudos_concursos.repository.MateriaRepository;
import com.ricardosantos.estudos_concursos.repository.RegistroDiarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DashboardService {

    private final MateriaRepository materiaRepository;
    private final RegistroDiarioRepository registroRepository;
    private final RevisaoService revisaoService;
    private final RegistroService registroService;

    public double getTotalHorasEstudadas() {
        int totalMinutos = registroService.calcularTotalMinutosEstudados();
        return Math.round((totalMinutos / 60.0) * 10.0) / 10.0;
    }

    public int getTotalQuestoes() {
        return registroService.calcularTotalQuestoes();
    }

    public double getPercentualMedioAcerto() {
        int totalQuestoes = registroService.calcularTotalQuestoes();
        if (totalQuestoes == 0) return 0;
        int totalAcertos = registroService.calcularTotalAcertos();
        return Math.round((totalAcertos * 100.0 / totalQuestoes) * 10.0) / 10.0;
    }

    public List<Materia> getMateriasComProgresso() {
        return materiaRepository.findAllByOrderByPesoDesc();
    }

    public List<Revisao> getRevisoesPendentesHoje() {
        return revisaoService.listarPendentesHoje();
    }

    public long getQuantidadeRevisoesPendentes() {
        return revisaoService.contarPendentesHoje();
    }

    public List<RegistroDiario> getUltimosRegistros() {
        return registroService.listarUltimos(5);
    }

    public String getMateriasMaisEstudada() {
        return registroRepository.findAll()
                .stream()
                .filter(r -> r.getMateria() != null && r.getTempoReal() != null)
                .collect(Collectors.groupingBy(
                        r -> r.getMateria().getNome(),
                        Collectors.summingInt(RegistroDiario::getTempoReal)
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Nenhum registro ainda");
    }

    public long getTotalMaterias() {
        return materiaRepository.count();
    }

    // ── CONSTÂNCIA — 31 dias ─────────────────────────────────────────────────

    public List<Map<String, Object>> getConstanciaUltimos30Dias() {
        List<RegistroDiario> registros = registroRepository.findAll();
        Set<LocalDate> diasEstudados = registros.stream()
                .filter(r -> r.getData() != null)
                .map(RegistroDiario::getData)
                .collect(Collectors.toSet());

        List<Map<String, Object>> constancia = new ArrayList<>();
        LocalDate hoje = LocalDate.now();

        String[] meses = {"Jan","Fev","Mar","Abr","Mai","Jun",
                "Jul","Ago","Set","Out","Nov","Dez"};

        for (int i = 30; i >= 0; i--) {
            LocalDate dia = hoje.minusDays(i);
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("data", dia.toString());
            entry.put("diaMes", dia.getDayOfMonth());
            entry.put("mes", meses[dia.getMonthValue() - 1]);
            entry.put("diaCompleto",
                    dia.getDayOfMonth() + "/" +
                            String.format("%02d", dia.getMonthValue()) + "/" +
                            dia.getYear());
            entry.put("estudou", diasEstudados.contains(dia));
            entry.put("hoje", dia.equals(hoje));
            constancia.add(entry);
        }
        return constancia;
    }

    public int getDiasSequencia() {
        List<RegistroDiario> registros = registroRepository.findAll();
        Set<LocalDate> diasEstudados = registros.stream()
                .filter(r -> r.getData() != null)
                .map(RegistroDiario::getData)
                .collect(Collectors.toSet());

        int sequencia = 0;
        LocalDate dia = LocalDate.now();
        while (diasEstudados.contains(dia)) {
            sequencia++;
            dia = dia.minusDays(1);
        }
        return sequencia;
    }

    public int getRecordeDias() {
        List<RegistroDiario> registros = registroRepository.findAll();
        List<LocalDate> dias = registros.stream()
                .filter(r -> r.getData() != null)
                .map(RegistroDiario::getData)
                .distinct()
                .sorted()
                .toList();

        if (dias.isEmpty()) return 0;

        int recorde = 1;
        int atual = 1;
        for (int i = 1; i < dias.size(); i++) {
            long diff = ChronoUnit.DAYS.between(dias.get(i - 1), dias.get(i));
            if (diff == 1) {
                atual++;
                recorde = Math.max(recorde, atual);
            } else {
                atual = 1;
            }
        }
        return recorde;
    }

    // ── PAINEL DE DISCIPLINAS ────────────────────────────────────────────────

    public List<Map<String, Object>> getPainelDisciplinas() {
        List<Materia> materias = materiaRepository.findAllByOrderByPesoDesc();
        List<RegistroDiario> todos = registroRepository.findAll();
        List<Map<String, Object>> painel = new ArrayList<>();

        for (Materia m : materias) {
            List<RegistroDiario> regMateria = todos.stream()
                    .filter(r -> r.getMateria() != null
                            && r.getMateria().getId().equals(m.getId()))
                    .toList();

            int totalMin = regMateria.stream()
                    .filter(r -> r.getTempoReal() != null)
                    .mapToInt(RegistroDiario::getTempoReal).sum();

            int totalQ = regMateria.stream()
                    .filter(r -> r.getQuestoesResolvidas() != null)
                    .mapToInt(RegistroDiario::getQuestoesResolvidas).sum();

            int totalAcertos = regMateria.stream()
                    .filter(r -> r.getAcertos() != null)
                    .mapToInt(RegistroDiario::getAcertos).sum();

            int totalErros = totalQ - totalAcertos;

            double pct = totalQ > 0
                    ? Math.round((totalAcertos * 100.0 / totalQ) * 10.0) / 10.0
                    : 0;

            int horas = totalMin / 60;
            int min = totalMin % 60;
            String tempoFormatado = horas > 0
                    ? horas + "h" + String.format("%02d", min) + "min"
                    : min > 0 ? min + "min" : "-";

            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("nome", m.getNome());
            entry.put("tempo", tempoFormatado);
            entry.put("totalMin", totalMin);
            entry.put("acertos", totalAcertos);
            entry.put("erros", totalErros < 0 ? 0 : totalErros);
            entry.put("totalQuestoes", totalQ);
            entry.put("percentual", pct);
            painel.add(entry);
        }
        return painel;
    }

    // ── GRÁFICO SEMANAL ──────────────────────────────────────────────────────

    public List<Map<String, Object>> getDadosSemanais() {
        LocalDate hoje = LocalDate.now();
        List<RegistroDiario> registros = registroRepository.findAll();
        List<Map<String, Object>> semana = new ArrayList<>();

        String[] diasSemana = {"DOM","SEG","TER","QUA","QUI","SEX","SAB"};

        for (int i = 6; i >= 0; i--) {
            LocalDate dia = hoje.minusDays(i);
            String nomeDia = diasSemana[dia.getDayOfWeek().getValue() % 7];

            List<RegistroDiario> regsNoDia = registros.stream()
                    .filter(r -> r.getData() != null
                            && r.getData().equals(dia))
                    .toList();

            int minutos = regsNoDia.stream()
                    .filter(r -> r.getTempoReal() != null)
                    .mapToInt(RegistroDiario::getTempoReal).sum();

            int questoes = regsNoDia.stream()
                    .filter(r -> r.getQuestoesResolvidas() != null)
                    .mapToInt(RegistroDiario::getQuestoesResolvidas).sum();

            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("dia", nomeDia);
            entry.put("minutos", minutos);
            entry.put("horas", Math.round((minutos / 60.0) * 10.0) / 10.0);
            entry.put("questoes", questoes);
            entry.put("hoje", dia.equals(hoje));
            semana.add(entry);
        }
        return semana;
    }

    // ── METAS SEMANAIS ───────────────────────────────────────────────────────

    public double getHorasEstudadasSemana() {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioSemana = hoje.minusDays(
                hoje.getDayOfWeek().getValue() - 1);

        int minutos = registroRepository.findAll().stream()
                .filter(r -> r.getData() != null
                        && !r.getData().isBefore(inicioSemana)
                        && !r.getData().isAfter(hoje)
                        && r.getTempoReal() != null)
                .mapToInt(RegistroDiario::getTempoReal).sum();

        return Math.round((minutos / 60.0) * 10.0) / 10.0;
    }

    public int getQuestoesSemana() {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioSemana = hoje.minusDays(
                hoje.getDayOfWeek().getValue() - 1);

        return registroRepository.findAll().stream()
                .filter(r -> r.getData() != null
                        && !r.getData().isBefore(inicioSemana)
                        && !r.getData().isAfter(hoje)
                        && r.getQuestoesResolvidas() != null)
                .mapToInt(RegistroDiario::getQuestoesResolvidas).sum();
    }
}