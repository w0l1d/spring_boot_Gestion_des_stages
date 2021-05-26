package com.storactive.stg.controller;

import com.storactive.stg.model.Stagiaire;
import com.storactive.stg.service.StagiaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/stagiaires")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class StagiaireController {

    final StagiaireService stagiaireSer;

    @Autowired
    public StagiaireController(StagiaireService stagiaireSer) {
        this.stagiaireSer = stagiaireSer;
    }

    @GetMapping({"/", ""})
    public String getStagiaires(Model model) {
        model.addAttribute("stagiaire", new Stagiaire());
        model.addAttribute("stagiaires", stagiaireSer.getAll());
        return "stagiaires";
    }


    @PostMapping({"/", ""})
    public String postAddStagiaire(@ModelAttribute @Valid Stagiaire stagiaire, Model model) {
        stagiaireSer.create(stagiaire);

        model.addAttribute("stagiaires", stagiaireSer.getAll());
        return "stagiaires";
    }


    @GetMapping({"/{id}"})
    public String getUpdateStagiaire(@PathVariable Integer id, Model model) {
        Stagiaire stagiaire = stagiaireSer.findById(id);
        model.addAttribute("stagiaire", stagiaire);
        return "updateStagiaire";
    }

    @PostMapping({"/{id}"})
    public String putUpdateStagiaire(@PathVariable Integer id,
                                     @ModelAttribute @Valid Stagiaire stagiaire,
                                     Model model) {
        stagiaire.setId(id);
        stagiaireSer.update(stagiaire);
        model.addAttribute("msg_updated", true);
        model.addAttribute("stagiaires", stagiaireSer.getAll());
        return "stagiaires";
    }

    @PostMapping({"/{id}/delete"})
    public String deleteStagiaire(@PathVariable Integer id,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        stagiaireSer.delete(id);
        model.addAttribute("msg_deleted", true);
        return "redirect:/stagiaires?deleted";
    }

}
