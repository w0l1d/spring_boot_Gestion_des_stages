package com.storactive.stg.controller;

import com.storactive.stg.model.Stagiaire;
import com.storactive.stg.service.iService.IStagiaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/stagiaires")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class StagiaireController {

    final IStagiaireService stagiaireSer;

    @Autowired
    public StagiaireController(IStagiaireService stagiaireSer) {
        this.stagiaireSer = stagiaireSer;
    }

    @GetMapping({"/", ""})
    public String getIndex(Model model) {
        model.addAttribute("stagiaire", new Stagiaire());
        model.addAttribute("stagiaires", stagiaireSer.getAll());
        return "stagiaire/index";
    }


    @PostMapping({"/", ""})
    public String postAddStagiaire(@ModelAttribute @Valid Stagiaire stagiaire, Model model) {
        stagiaireSer.create(stagiaire);

        model.addAttribute("stagiaires", stagiaireSer.getAll());
        return "stagiaire/index";
    }


    @GetMapping({"/{id}"})
    public String getUpdateStagiaire(@PathVariable Integer id, Model model) {
        Stagiaire stagiaire = stagiaireSer.findById(id);
        model.addAttribute("stagiaire", stagiaire);
        return "stagiaire/update";
    }

    @PostMapping({"/{id}"})
    public String putUpdateStagiaire(@PathVariable Integer id,
                                     @ModelAttribute @Valid Stagiaire stagiaire,
                                     Model model) {
        stagiaire.setId(id);
        stagiaireSer.update(stagiaire);
        model.addAttribute("msg_updated", true);
        model.addAttribute("stagiaires", stagiaireSer.getAll());
        return "stagiaire/index";
    }

    @GetMapping({"/{id}/delete"})
    public String deleteStagiaire(@PathVariable Integer id,
                                  Model model) {
        stagiaireSer.delete(id);
        model.addAttribute("msg_deleted", true);
        return "redirect:/stagiaires?deleted";
    }

}
