package com.storactive.stg.controller;

import com.storactive.stg.model.*;
import com.storactive.stg.model.enums.Gender;
import com.storactive.stg.service.EmployeeService;
import com.storactive.stg.service.InternerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class DashboardController {

    final EmployeeService employeeSer;
    final InternerService internerSer;

    @Autowired
    public DashboardController(EmployeeService employeeSer, InternerService internerSer) {
        this.employeeSer = employeeSer;
        this.internerSer = internerSer;
    }

    @GetMapping("blank")
    public String getBlank(Model model) {
        return "blank";
    }

    @GetMapping("insert")
    public String getInsert() {
        Employee employee = new Employee();
        employee.setName("walid ah");
        employee.setCin("VA145962");
        employee.setEmail("walid@ah.com");
        employee.setPassword("password");
        employee.setUsername("admin");
        employeeSer.create(employee);

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

    @GetMapping("test")
    public String getTest(RedirectAttributes redirectAttributes,
                          Model model) {
        redirectAttributes.addAttribute("msg_deleted", true);
        model.addAttribute("internship", new Internship());
        return "fragments/add_internship";
    }


    @GetMapping("test2")
    public String getTest2(RedirectAttributes redirectAttributes,
                           Model model) {
        redirectAttributes.addAttribute("msg_deleted", true);
        model.addAttribute("task", new Task());
        return "fragments/add_task";
    }


    @GetMapping("test3")
    public String getTest3(RedirectAttributes redirectAttributes,
                           Model model) {
        redirectAttributes.addAttribute("msg_deleted", true);
        model.addAttribute("absence", new Absence());
        return "fragments/add_absence";
    }

    @GetMapping("test4")
    public String getTest4(RedirectAttributes redirectAttributes,
                           Model model) {
        redirectAttributes.addAttribute("msg_deleted", true);
        model.addAttribute("employee", new Employee());
        return "fragments/add_employee";
    }


    @GetMapping("test5")
    public String getTest5(RedirectAttributes redirectAttributes,
                           Model model) {
        redirectAttributes.addAttribute("msg_deleted", true);
        model.addAttribute("interner", new Interner());
        return "fragments/add_interner";
    }

    @GetMapping("profile")
    public String getProfile(Principal principal, Model model) {
        String username = principal.getName();
        Employee employee = employeeSer.findByUsername(username);
        model.addAttribute("isProfile", true);
        model.addAttribute("employee", employee);

        return "employee/update";
    }






/*
    @GetMapping("stages")
    public String getStage(Model model) {
        return "stage";
    }

    @GetMapping("historiques")
    public String getHistory(Model model) {
        return "history";
    }

    @GetMapping("taches")
    public String getTache(Model model) {
        return "tache";
    }

    @GetMapping("utilisateurs")
    public String getUser(Model model) {
        return "user";
    }

    @GetMapping("stages")
    public String getArchive(Model model) {
        return "archive";
    }
*/


}
