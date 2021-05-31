package com.storactive.stg.controller;

import com.storactive.stg.model.Absence;
import com.storactive.stg.model.Employee;
import com.storactive.stg.model.Internship;
import com.storactive.stg.model.Task;
import com.storactive.stg.service.EmployeeService;
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

    @Autowired
    public DashboardController(EmployeeService employeeSer) {
        this.employeeSer = employeeSer;
    }

    @GetMapping("blank")
    public String getBlank(Model model) {
        return "blank";
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
