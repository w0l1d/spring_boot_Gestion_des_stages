package com.storactive.stg.controller;

import com.storactive.stg.Utils;
import com.storactive.stg.model.Employee;
import com.storactive.stg.service.EmployeeService;
import com.storactive.stg.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/history")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class HistoryController {

    final HistoryService historySer;
    final EmployeeService employeeSer;


    @GetMapping({"/", ""})
    public String getIndex(Model model) {
        return "history/index";
    }

    @GetMapping("/delete-log")
    public String deleteLog(Model model) {
        historySer.deleteAll();
        model.addAttribute("log_deleted", true);
        return "history/index";
    }


    @GetMapping("/delete-my-log")
    public String deleteMyLog1(Model model) {
        historySer.deleteHistoryByUser(Utils.getCurrUser().getId());
        model.addAttribute("my_log_deleted", true);
        return "history/index";
    }


    @GetMapping("/my-log")
    public String getMyLog(Model model) {
        Employee employee = (Employee) Utils.getCurrUser();
        model.addAttribute("actions", employee.getHistories());
        model.addAttribute("myLog", true);
        return "history/index";
    }


    @GetMapping("/my-log/delete")
    public String deleteMyLog0(Model model) {
        historySer.deleteHistoryByUser(Utils.getCurrUser().getId());
        model.addAttribute("my_log_deleted", true);
        return "history/index";
    }


}
