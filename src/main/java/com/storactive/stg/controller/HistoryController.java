package com.storactive.stg.controller;

import com.storactive.stg.model.Employee;
import com.storactive.stg.service.EmployeeService;
import com.storactive.stg.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/history")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class HistoryController {

    final HistoryService historySer;
    final EmployeeService employeeSer;

    @Autowired
    public HistoryController(HistoryService historySer, EmployeeService employeeSer) {
        this.historySer = historySer;
        this.employeeSer = employeeSer;
    }

    @GetMapping({"/", ""})
    public String getIndex(Model model) {
        model.addAttribute("actions", historySer.getAll());
        return "history/index";
    }


    @GetMapping("/mine")
    public String getMyLog(Model model,
                           Principal principal) {
        Employee employee = employeeSer.findByUsername(principal.getName());
        model.addAttribute("actions", employee.getHistories());
        model.addAttribute("myLog", true);
        return "history/index";
    }

    @GetMapping("/deleteLog")
    public String deleteLog(Model model) {
        historySer.deleteAll();
        model.addAttribute("actions", historySer.getAll());
        model.addAttribute("log_deleted", true);
        return "history/index";
    }

    @GetMapping("/mine/delete")
    public String deleteMyLog(Model model,
                              Principal principal) {
        Employee employee = employeeSer.findByUsername(principal.getName());
        historySer.deleteAllSpecified(employee.getHistories());
        model.addAttribute("actions", historySer.getAll());
        model.addAttribute("my_log_deleted", true);
        return "history/index";
    }


}
