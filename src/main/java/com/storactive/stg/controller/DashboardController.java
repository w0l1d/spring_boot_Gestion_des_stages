package com.storactive.stg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class DashboardController {

    @GetMapping("blank")
    public String getBlank(Model model) {
        return "blank";
    }

    @GetMapping("test")
    public String getTest(RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("msg_deleted", true);
        return "redirect:/stagiaires/";
    }




/*
    @GetMapping("stages")
    public String getStage(Model model) {
        return "stage";
    }

    @GetMapping("historiques")
    public String getHistory(Model model) {
        return "history";
    }

    @GetMapping("taches")
    public String getTache(Model model) {
        return "tache";
    }

    @GetMapping("utilisateurs")
    public String getUser(Model model) {
        return "user";
    }

    @GetMapping("stages")
    public String getArchive(Model model) {
        return "archive";
    }
*/


}
