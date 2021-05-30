package com.storactive.stg.controller;

import com.storactive.stg.Utils;
import com.storactive.stg.model.Employee;
import com.storactive.stg.model.Interner;
import com.storactive.stg.model.enums.Gender;
import com.storactive.stg.service.EmployeeService;
import com.storactive.stg.service.InternerService;
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
    final InternerService internerSer;
    final BCryptPasswordEncoder encoder;

    @Autowired
    public HomeController(EmployeeService userSer, InternerService internerSer, BCryptPasswordEncoder encoder) {
        this.userSer = userSer;
        this.internerSer = internerSer;
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

        Interner interner = new Interner();
        interner.setName("walid ah");
        interner.setCin("VA148862");
        interner.setEmail("walid@ah.com");
        interner.setPassword("password");
        interner.setUsername("admin2");
        interner.setGender(Gender.F);
        interner.setAddress("dsfsdfsdfsdgs s gsgdg");
        interner.setPhone("0606695961");
        internerSer.create(interner);

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
