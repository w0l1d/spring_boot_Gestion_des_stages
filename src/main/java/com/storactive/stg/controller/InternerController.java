package com.storactive.stg.controller;

import com.storactive.stg.model.Interner;
import com.storactive.stg.service.InternerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/interners")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class InternerController {

    final InternerService internerSer;

    @Autowired
    public InternerController(InternerService internerSer) {
        this.internerSer = internerSer;
    }

    @GetMapping({"/", ""})
    public String getIndex(Model model) {
        model.addAttribute("interner", new Interner());
        model.addAttribute("interners", internerSer.getAll());
        return "interner/index";
    }


    @PostMapping({"/", ""})
    public String postAddInterner(@ModelAttribute @Valid Interner interner, Model model) {
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
                                     Model model) {
        interner.setId(id);
        internerSer.update(interner);
        model.addAttribute("msg_updated", true);
        model.addAttribute("interners", internerSer.getAll());
        return "interner/index";
    }

    @GetMapping({"/{id}/delete"})
    public String deleteInterner(@PathVariable Integer id,
                                  Model model) {
        internerSer.delete(id);
        model.addAttribute("msg_deleted", true);
        return "redirect:/interners?deleted";
    }

}
