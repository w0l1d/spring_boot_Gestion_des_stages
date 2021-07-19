package com.storactive.stg.controller;

import com.storactive.stg.Utils;
import com.storactive.stg.service.EmployeeService;
import com.storactive.stg.service.InternerService;
import com.storactive.stg.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    final BCryptPasswordEncoder encoder;
    final EmployeeService userSer;
    final InternerService internerSer;
    final EmployeeService employeeSer;
    final StageService stageSer;


    @GetMapping({"", "home"})
    public String home(Model model) {
        if (!Utils.isAuthenticated())
            return "redirect:/login";

        model.addAttribute("countInterners", internerSer.countAll())
                .addAttribute("countActiveInterners", internerSer.countAllActive())
                .addAttribute("countEmployees", employeeSer.count())
                .addAttribute("countInternships", stageSer.countAll())
                .addAttribute("countActiveInternships", stageSer.countAllActive());


        return "index_Dashboard";

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
