package com.storactive.stg.controller;

import com.storactive.stg.Utils;
import com.storactive.stg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    final UserService userSer;
    final BCryptPasswordEncoder encoder;

    @Autowired
    public HomeController(UserService userSer, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userSer = userSer;
        this.encoder = bCryptPasswordEncoder;
    }


    @GetMapping
    public String home(Model model) {
        if (Utils.isAuthenticated())
            return "dashboard";

        model.addAttribute("name", "walid");

//        Employee employee = new Employee();
//        employee.setName("walid ah");
//        employee.setCin("VA145962");
//        employee.setEmail("walid@ah.com");
//        employee.setPassword("password");
//        employee.setUsername("admin");
//        userSer.create(employee);

        return "greeting";
    }


    @GetMapping("login")
    public String getLogin() {
        return "login";
    }


    @GetMapping("error")
    public String getError() {
        return "xxx";
    }





}
