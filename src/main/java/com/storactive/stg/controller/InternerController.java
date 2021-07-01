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
        model.addAttribute("interners", internerSer.getAll());
        return "interner/index";
    }


    @PostMapping({"/", ""})
    public String postAddInterner(@ModelAttribute @Valid Interner interner,
                                  Model model,
                                  BindingResult bindingResult) {

        if (!bindingResult.hasErrors())
            internerSer.create(interner);

        model.addAttribute("interners", internerSer.getAll());
        return "interner/index";
    }


    @GetMapping({"/{id}"})
    public String getUpdateInterner(@PathVariable Integer id, Model model) {
        Interner interner = internerSer.findById(id);
        model.addAttribute("interner", interner);
        return "interner/update";
    }

    @PostMapping({"/{id}"})
    public String putUpdateInterner(@PathVariable Integer id,
                                    @ModelAttribute @Valid Interner interner,
                                    Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "interner/update";

        interner.setId(id);
        internerSer.update(interner);

        model.addAttribute("interners", internerSer.getAll())
                .addAttribute("msg_updated", true);
        return "interner/index";
    }

    @GetMapping({"/{id}/delete"})
    public String deleteInterner(@PathVariable Integer id,
                                 Model model) {
        internerSer.delete(id);
        model.addAttribute("msg_deleted", true);
        return "redirect:/interners?deleted";
    }

    @GetMapping("/{id}/internship")
    public String getAddInternship(Model model, @PathVariable Integer id) {
        model.addAttribute("internship", new Internship());
        return "interner/add_internship";
    }

    @PostMapping("/{id}/internship")
    @Transactional
    public String postAddInternship(@PathVariable Integer id,
                                    @ModelAttribute @Valid Internship internship,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "interner/add_internship";

        Interner interner = internerSer.findById(id);

        internship = stageSer.create(internship);

        internship.setInterner(interner);

        interner.getInternships().add(internship);


        return "redirect:/internships?inserted";
    }


}
