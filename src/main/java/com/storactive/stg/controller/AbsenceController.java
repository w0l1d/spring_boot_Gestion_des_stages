package com.storactive.stg.controller;

import com.storactive.stg.Utils;
import com.storactive.stg.model.Absence;
import com.storactive.stg.model.Internship;
import com.storactive.stg.service.AbsenceService;
import com.storactive.stg.service.InternerService;
import com.storactive.stg.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Controller
@RequestMapping("/absences")
@RequiredArgsConstructor
public class AbsenceController {

    final AbsenceService absenceSer;
    final InternerService internerSer;
    final StageService stageSer;


    @GetMapping({"/", ""})
    public String getIndex(Model model) {
        model.addAttribute("absence", new Absence());
        return "absence/index";
    }

    @PostMapping({"/", ""})
    public String postAbsence(Model model,
                              HttpServletRequest request,
                              @ModelAttribute @Valid Absence absence,
                              BindingResult bindingResult,
                              @NotNull @RequestParam("internship-select") Integer internshipId) {
        if (bindingResult.hasErrors()) {
            return "absence/index";
        }

        Internship internship = stageSer.findById(internshipId);

        Utils.assertAuthorizedToResource(request, internship.getInterner());

        absence.setInternship(internship);
        absence = absenceSer.create(absence);
        internship.getAbsences().add(absence);

        return "redirect:/absences?inserted";
    }


    @GetMapping("/{id}/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getUpdateAbsence(@NotNull @Positive @PathVariable Integer id,
                                   Model model) {
        Absence absence = absenceSer.findById(id);
        model.addAttribute("absence", absence);
        return "absence/update";
    }


    @PostMapping("/{id}/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String putUpdateAbsence(@NotNull @Positive @PathVariable Integer id,
                                   @ModelAttribute @Valid Absence absence,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors())
            return "absence/update";

        absence.setId(id);
        absenceSer.update(absence);

        model.addAttribute("msg_updated", true);
        return "redirect:/absences?updated";
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
