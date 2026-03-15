package com.ricardosantos.estudos_concursos.controller;

import com.ricardosantos.estudos_concursos.model.Revisao;
import com.ricardosantos.estudos_concursos.service.MateriaService;
import com.ricardosantos.estudos_concursos.service.RevisaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/revisoes")
@RequiredArgsConstructor
public class RevisaoController {

    private final RevisaoService revisaoService;
    private final MateriaService materiaService;

    // Lista todas as revisões
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("revisoes", revisaoService.listarTodas());
        model.addAttribute("revisao", new Revisao());
        model.addAttribute("materias", materiaService.listarTodas());
        model.addAttribute("pendentesHoje",
                revisaoService.listarPendentesHoje());
        return "revisoes";
    }

    // Salva uma nova revisão
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Revisao revisao,
                         RedirectAttributes redirectAttributes) {
        revisaoService.salvar(revisao);
        redirectAttributes.addFlashAttribute("sucesso",
                "Revisão criada com sucesso!");
        return "redirect:/revisoes";
    }

    // Marca uma revisão como concluída
    @GetMapping("/concluir/{id}/{numero}")
    public String concluir(@PathVariable Long id,
                           @PathVariable int numero,
                           RedirectAttributes redirectAttributes) {
        revisaoService.concluirRevisao(id, numero);
        redirectAttributes.addFlashAttribute("sucesso",
                "Revisão marcada como concluída!");
        return "redirect:/revisoes";
    }

    // Deleta uma revisão
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id,
                          RedirectAttributes redirectAttributes) {
        revisaoService.deletar(id);
        redirectAttributes.addFlashAttribute("sucesso",
                "Revisão removida com sucesso!");
        return "redirect:/revisoes";
    }
}
