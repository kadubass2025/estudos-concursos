package com.ricardosantos.estudos_concursos.controller;

import com.ricardosantos.estudos_concursos.model.RegistroDiario;
import com.ricardosantos.estudos_concursos.model.enums.TipoEstudo;
import com.ricardosantos.estudos_concursos.service.MateriaService;
import com.ricardosantos.estudos_concursos.service.RegistroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registros")
@RequiredArgsConstructor
public class RegistroController {

    private final RegistroService registroService;
    private final MateriaService materiaService;

    // Lista todos os registros
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("registros", registroService.listarTodos());
        model.addAttribute("registro", new RegistroDiario());
        model.addAttribute("materias", materiaService.listarTodas());
        model.addAttribute("tipos", TipoEstudo.values());
        return "registros";
    }

    // Salva um novo registro
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute RegistroDiario registro,
                         RedirectAttributes redirectAttributes) {
        registroService.salvar(registro);
        redirectAttributes.addFlashAttribute("sucesso",
                "Registro salvo com sucesso!");
        return "redirect:/registros";
    }

    // Abre formulário para editar
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("registro", registroService.buscarPorId(id));
        model.addAttribute("registros", registroService.listarTodos());
        model.addAttribute("materias", materiaService.listarTodas());
        model.addAttribute("tipos", TipoEstudo.values());
        return "registros";
    }

    // Deleta um registro
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id,
                          RedirectAttributes redirectAttributes) {
        registroService.deletar(id);
        redirectAttributes.addFlashAttribute("sucesso",
                "Registro removido com sucesso!");
        return "redirect:/registros";
    }
}
