package com.storactive.stg.controller;

import com.storactive.stg.model.Internship;
import com.storactive.stg.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/internships")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class InternshipsController {

    final StageService stageService;

    @Autowired
    public InternshipsController(StageService stageService) {
        this.stageService = stageService;
    }

    @GetMapping({"/", ""})
    public String getIndex(Model model) {
        model.addAttribute("internship", new Internship());
        model.addAttribute("internships", stageService.getAll());
        return "internship/index";
    }


    @PostMapping({"/", ""})
    public String postAddInternship(@ModelAttribute @Valid Internship internship, Model model) {
        stageService.create(internship);

        model.addAttribute("internships", stageService.getAll());
        return "internship/index";
    }


    @GetMapping({"/{id}"})
    public String getUpdateInternship(@PathVariable Integer id, Model model) {
        Internship internship = stageService.findById(id);
        model.addAttribute("internship", internship);
        return "internship/update";
    }

    @PostMapping({"/{id}"})
    public String putUpdateInternship(@PathVariable Integer id,
                                     @ModelAttribute @Valid Internship internship,
                                     Model model) {
        internship.setId(id);
        stageService.update(internship);
        model.addAttribute("msg_updated", true);
        model.addAttribute("internships", stageService.getAll());
        return "internship/index";
    }

    @GetMapping({"/{id}/delete"})
    public String deleteInternship(@PathVariable Integer id,
                                  Model model) {
        stageService.delete(id);
        model.addAttribute("msg_deleted", true);
        return "redirect:/internships?deleted";
    }

}
