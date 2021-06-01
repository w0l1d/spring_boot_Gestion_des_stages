package com.storactive.stg.controller;

import com.storactive.stg.model.Absence;
import com.storactive.stg.service.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Controller
@RequestMapping("/absences")
public class AbsenceController {

    final AbsenceService absenceSer;

    @Autowired
    public AbsenceController(AbsenceService absenceSer) {
        this.absenceSer = absenceSer;
    }


    @GetMapping({"/", ""})
    public String getIndex(Model model) {
        model.addAttribute("absence", new Absence());
        model.addAttribute("absences", absenceSer.getAll());
        return "absence/index";
    }


    @PostMapping({"/", ""})
    public String postAddAbsence(@ModelAttribute @Valid Absence absence, Model model) {
        absenceSer.create(absence);

        model.addAttribute("absences", absenceSer.getAll());
        return "absence/index";
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getUpdateAbsence(@NotNull @Positive @PathVariable Integer id,
                                   Model model) {
        Absence absence = absenceSer.findById(id);
        model.addAttribute("absence", absence);
        return "absence/update";
    }


    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String putUpdateAbsence(@NotNull @Positive @PathVariable Integer id,
                                   @ModelAttribute @Valid Absence absence,
                                   Model model) {
        absence.setId(id);
        absenceSer.update(absence);

        model.addAttribute("msg_updated", true);
        model.addAttribute("absences", absenceSer.getAll());
        return "absence/index";
    }

    @GetMapping("/{id}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteAbsence(@PathVariable Integer id,
                                Model model) {
        absenceSer.delete(id);
        model.addAttribute("msg_deleted", true);
        return "redirect:/absences?deleted";
    }

}
