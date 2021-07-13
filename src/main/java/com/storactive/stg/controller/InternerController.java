package com.storactive.stg.controller;

import com.storactive.stg.model.Interner;
import com.storactive.stg.model.Internship;
import com.storactive.stg.service.InternerService;
import com.storactive.stg.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/interners")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class InternerController {

    final InternerService internerSer;
    final StageService stageSer;

    @Autowired
    public InternerController(InternerService internerSer, StageService stageSer) {
        this.internerSer = internerSer;
        this.stageSer = stageSer;
    }

    @GetMapping({"/", ""})
    public String getIndex(Model model) {
        model.addAttribute("interner", new Interner());
        return "interner/index";
    }


    @GetMapping("/{id}")
    public String getInterner(@PathVariable @Valid @NotNull Integer id,
                              Model model) {
        Interner interner = internerSer.findById(id);
        model.addAttribute(interner)
                .addAttribute("internship", new Internship());
        return "interner/view";
    }


    @PostMapping({"/", ""})
    public String postAddInterner(@ModelAttribute @Valid Interner interner,
                                  Model model,
                                  BindingResult bindingResult) {

        if (!bindingResult.hasErrors())
            internerSer.create(interner);

        return "interner/index";
    }


    @GetMapping({"/{id}/update"})
    public String getUpdateInterner(@PathVariable @Valid @NotNull Integer id,
                                    Model model) {
        Interner interner = internerSer.findById(id);
        model.addAttribute("interner", interner);
        return "interner/update";
    }

    @PostMapping("/{id}/update")
    public String putUpdateInterner(@PathVariable @Valid @NotNull Integer id,
                                    @ModelAttribute @Valid Interner interner,
                                    Model model,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "interner/update";

        interner.setId(id);
        internerSer.update(interner);

        model.addAttribute("msg_updated", true);
        return "redirect:/interners/" + id;
    }

    @GetMapping("/{id}/delete")
    public String deleteInterner(@PathVariable @Valid @NotNull Integer id,
                                 Model model) {
        internerSer.delete(id);
        model.addAttribute("msg_deleted", true);
        return "redirect:/interners?deleted";
    }

    @GetMapping("/{id}/internship")
    public String getAddInternship(Model model, @PathVariable @Valid @NotNull Integer id) {
        model.addAttribute("internship", new Internship());
        return "add_internship_page";
    }

    @PostMapping({"/{id}", "/{id}/internship"})
    @Transactional
    public String postAddInternship(@PathVariable @Valid @NotNull Integer id,
                                    @ModelAttribute @Valid Internship internship,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "add_internship_page";

        Interner interner = internerSer.findById(id);

        internship.setInterner(interner);
        stageSer.create(internship);


        return "redirect:/internships?inserted";
    }


}
