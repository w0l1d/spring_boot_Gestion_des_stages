package com.storactive.stg.controller;

import com.storactive.stg.model.Employee;
import com.storactive.stg.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class EmployeeController {

    final EmployeeService employeeSer;

    @Autowired
    public EmployeeController(EmployeeService employeeSer) {
        this.employeeSer = employeeSer;
    }


    @GetMapping({"/", ""})
    public String getIndex(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("employees", employeeSer.getAll());
        return "employee/index";
    }


    @PostMapping({"/", ""})
    public String postAddEmployee(@ModelAttribute @Valid Employee employee,
                                  BindingResult bindingResult,
                                  Model model) {

        if (!bindingResult.hasErrors()) {
            employeeSer.create(employee);
            model.addAttribute("msg_inserted", true)
                    .addAttribute("employee", new Employee());
        }

        model.addAttribute("employees", employeeSer.getAll());

        return "employee/index";
    }


    @GetMapping({"/{id}"})
    public String getUpdateEmployee(@PathVariable Integer id, Model model) {
        Employee employee = employeeSer.findById(id);
        model.addAttribute("employee", employee);
        return "employee/update";
    }


    @PostMapping({"/{id}"})
    public String putUpdateEmployee(@NotNull @Positive @PathVariable Integer id,
                                    @ModelAttribute @Valid Employee employee,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors())
            return "employee/update";

        employee.setId(id);
        employeeSer.update(employee);

        model.addAttribute("msg_updated", true);
        model.addAttribute("employees", employeeSer.getAll());
        return "employee/index";
    }

    @GetMapping({"/{id}/delete"})
    public String deleteEmployee(@PathVariable Integer id,
                                  Model model) {
        employeeSer.delete(id);
        model.addAttribute("msg_deleted", true);
        return "redirect:/internships?inserted";
    }

}
