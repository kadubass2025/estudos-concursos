package com.ricardosantos.estudos_concursos.controller;

import com.ricardosantos.estudos_concursos.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/")
    public String dashboard(Model model) {

        // KPIs principais
        model.addAttribute("totalHoras",
                dashboardService.getTotalHorasEstudadas());
        model.addAttribute("totalQuestoes",
                dashboardService.getTotalQuestoes());
        model.addAttribute("percentualAcerto",
                dashboardService.getPercentualMedioAcerto());
        model.addAttribute("totalMaterias",
                dashboardService.getTotalMaterias());

        // Listas
        model.addAttribute("materias",
                dashboardService.getMateriasComProgresso());
        model.addAttribute("revisoesPendentes",
                dashboardService.getRevisoesPendentesHoje());
        model.addAttribute("quantidadeRevisoes",
                dashboardService.getQuantidadeRevisoesPendentes());
        model.addAttribute("ultimosRegistros",
                dashboardService.getUltimosRegistros());
        model.addAttribute("materiaMaisEstudada",
                dashboardService.getMateriasMaisEstudada());

        // Constância
        model.addAttribute("constancia",
                dashboardService.getConstanciaUltimos30Dias());
        model.addAttribute("diasSequencia",
                dashboardService.getDiasSequencia());
        model.addAttribute("recordeDias",
                dashboardService.getRecordeDias());

        // Painel de disciplinas
        model.addAttribute("painelDisciplinas",
                dashboardService.getPainelDisciplinas());

        // Gráfico semanal
        model.addAttribute("dadosSemanais",
                dashboardService.getDadosSemanais());

        // Metas semanais — meta padrão: 25h e 135 questões por semana
        model.addAttribute("metaHorasSemana", 25.0);
        model.addAttribute("metaQuestoesSemana", 135);
        model.addAttribute("horasEstudadasSemana",
                dashboardService.getHorasEstudadasSemana());
        model.addAttribute("questoesSemana",
                dashboardService.getQuestoesSemana());

        return "dashboard";
    }
}