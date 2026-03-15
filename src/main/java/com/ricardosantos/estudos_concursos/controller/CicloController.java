package com.ricardosantos.estudos_concursos.controller;

import com.ricardosantos.estudos_concursos.model.CicloItem;
import com.ricardosantos.estudos_concursos.model.enums.Prioridade;
import com.ricardosantos.estudos_concursos.service.CicloService;
import com.ricardosantos.estudos_concursos.service.MateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ciclo")
@RequiredArgsConstructor
public class CicloController {

    private final CicloService cicloService;
    private final MateriaService materiaService;

    // Lista todos os itens do ciclo
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("itens", cicloService.listarTodos());
        model.addAttribute("cicloItem", new CicloItem());
        model.addAttribute("materias", materiaService.listarTodas());
        model.addAttribute("prioridades", Prioridade.values());
        model.addAttribute("totalMinutos",
                cicloService.calcularTotalMinutosCiclo());
        model.addAttribute("duracaoDias",
                cicloService.calcularDuracaoCicloEmDias());
        return "ciclo";
    }

    // Salva um item do ciclo
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute CicloItem cicloItem,
                         RedirectAttributes redirectAttributes) {
        cicloService.salvar(cicloItem);
        redirectAttributes.addFlashAttribute("sucesso",
                "Item do ciclo salvo com sucesso!");
        return "redirect:/ciclo";
    }

    // Deleta um item do ciclo
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id,
                          RedirectAttributes redirectAttributes) {
        cicloService.deletar(id);
        redirectAttributes.addFlashAttribute("sucesso",
                "Item removido com sucesso!");
        return "redirect:/ciclo";
    }
}

