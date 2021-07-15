package com.storactive.stg.controller;

import com.storactive.stg.Utils;
import com.storactive.stg.model.Interner;
import com.storactive.stg.service.EmployeeService;
import com.storactive.stg.service.InternerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/")
public class HomeController {

    final EmployeeService userSer;
    final InternerService internerSer;
    final BCryptPasswordEncoder encoder;

    @Autowired
    public HomeController(EmployeeService userSer,
                          InternerService internerSer,
                          BCryptPasswordEncoder encoder) {
        this.userSer = userSer;
        this.internerSer = internerSer;
        this.encoder = encoder;
    }


    @GetMapping({"", "home"})
    public String home(Model model) {
        if (Utils.isAuthenticated()) {
            model.addAttribute("interner", new Interner());
            return "index_Dashboard";
        }
        return "redirect:/login";
    }

    @PostMapping({"", "home"})
    public String postHome(Model model,
                           @NotNull @RequestParam("interner") Integer internerId) {

        return "redirect:/home";
    }


    @GetMapping("login")
    public String getLogin() {
        return "login";
    }


    @GetMapping("error")
    public String getError() {
        return "error";
    }

}
