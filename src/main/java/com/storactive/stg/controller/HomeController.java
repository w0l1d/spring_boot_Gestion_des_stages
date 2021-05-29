package com.storactive.stg.controller;

import com.storactive.stg.Utils;
import com.storactive.stg.model.Employee;
import com.storactive.stg.model.Stagiaire;
import com.storactive.stg.model.enums.Gender;
import com.storactive.stg.service.EmployeeService;
import com.storactive.stg.service.StagiaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    final EmployeeService userSer;
    final StagiaireService stagiaireSer;
    final BCryptPasswordEncoder encoder;

    @Autowired
    public HomeController(EmployeeService userSer, StagiaireService stagiaireSer, BCryptPasswordEncoder encoder) {
        this.userSer = userSer;
        this.stagiaireSer = stagiaireSer;
        this.encoder = encoder;
    }



    @GetMapping({"", "home"})
    public String home(Model model) {
        if (Utils.isAuthenticated())
            return "dashboard";
        model.addAttribute("name", "walid");
        Employee employee = new Employee();
        employee.setName("walid ah");
        employee.setCin("VA145962");
        employee.setEmail("walid@ah.com");
        employee.setPassword("password");
        employee.setUsername("admin");
        userSer.create(employee);

        Stagiaire stagiaire = new Stagiaire();
        stagiaire.setName("walid ah");
        stagiaire.setCin("VA148862");
        stagiaire.setEmail("walid@ah.com");
        stagiaire.setPassword("password");
        stagiaire.setUsername("admin2");
        stagiaire.setGender(Gender.F);
        stagiaire.setAddress("dsfsdfsdfsdgs s gsgdg");
        stagiaire.setPhone("0606695961");
        stagiaireSer.create(stagiaire);

        return "greeting";
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
