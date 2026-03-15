package com.ricardosantos.estudos_concursos.controller;

import com.ricardosantos.estudos_concursos.model.Materia;
import com.ricardosantos.estudos_concursos.model.enums.Prioridade;
import com.ricardosantos.estudos_concursos.service.MateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/materias")
@RequiredArgsConstructor
public class MateriaController {

    private final MateriaService materiaService;

    // Lista todas as matérias
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("materias", materiaService.listarTodas());
        model.addAttribute("materia", new Materia());
        model.addAttribute("prioridades", Prioridade.values());
        return "materias";
    }

    // Salva uma matéria nova ou atualiza existente
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Materia materia,
                         RedirectAttributes redirectAttributes) {
        materiaService.salvar(materia);
        redirectAttributes.addFlashAttribute("sucesso",
                "Matéria salva com sucesso!");
        return "redirect:/materias";
    }

    // Abre o formulário para editar uma matéria
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("materia", materiaService.buscarPorId(id));
        model.addAttribute("materias", materiaService.listarTodas());
        model.addAttribute("prioridades", Prioridade.values());
        return "materias";
    }

    // Deleta uma matéria
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id,
                          RedirectAttributes redirectAttributes) {
        materiaService.deletar(id);
        redirectAttributes.addFlashAttribute("sucesso",
                "Matéria removida com sucesso!");
        return "redirect:/materias";
    }
}