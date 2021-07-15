package com.storactive.stg.controller;

import com.storactive.stg.Utils;
import com.storactive.stg.service.EmployeeService;
import com.storactive.stg.service.InternerService;
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

    final EmployeeService userSer;
    final InternerService internerSer;
    final BCryptPasswordEncoder encoder;



    @GetMapping({"", "home"})
    public String home(Model model) {
        if (Utils.isAuthenticated())
            return "index_Dashboard";

        return "redirect:/login";
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
