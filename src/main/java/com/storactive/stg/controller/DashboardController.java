package com.storactive.stg.controller;

import com.storactive.stg.Utils;
import com.storactive.stg.model.*;
import com.storactive.stg.model.enums.Gender;
import com.storactive.stg.service.EmployeeService;
import com.storactive.stg.service.InternerService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.data.util.CastUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class DashboardController {

    final EmployeeService employeeSer;
    final InternerService internerSer;


    @GetMapping("blank")
    public String getBlank() {
        return "blank";
    }

    @GetMapping("insert")
    public String getInsert() {
        Employee employee = new Employee();
        employee.setName("walid ah");
        employee.setCin("VA145988");
        employee.setEmail("walid@ah55.com");
        employee.setPassword("password");
        employee.setUsername("admin2");

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

    @GetMapping("test0")
    public String getTest0(RedirectAttributes redirectAttributes,
                           Model model) {
        redirectAttributes.addAttribute("msg_deleted", true);
        model.addAttribute("absence", new Absence());
        return "absence/add_absence";
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


    @GetMapping("test6")
    public String getTest6(RedirectAttributes redirectAttributes,
                           Model model) {
        redirectAttributes.addAttribute("msg_deleted", true);
        model.addAttribute("pieces", new Piece[10]);
        return "fragments/add_file";
    }

    @GetMapping("test7")
    public String getTest7(RedirectAttributes redirectAttributes,
                           Model model) {
        redirectAttributes.addAttribute("msg_deleted", true);
        model.addAttribute("file", new Piece());
        return "fragments/add_piece";
    }

    @GetMapping("test8")
    public String getTest8(RedirectAttributes redirectAttributes,
                           Model model) {
        redirectAttributes.addAttribute("msg_deleted", true);
        model.addAttribute("internship", new Internship());
        return "internship/add_internship";
    }

    @GetMapping({"profile", "admin-profile"})
    public String getProfile(HttpServletRequest request, Model model) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            model.addAttribute("employee", (Employee) Utils.getCurrUser());
            return "employee/profile";
        }

        model.addAttribute("interner", (Interner) Utils.getCurrUser());
        return "interner/profile";
    }

    @PostMapping("profile")
    @PreAuthorize("hasRole('ROLE_INTERNER')")
    public String postProfile(@ModelAttribute @Valid Interner interner,
                              BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors())
            return "interner/profile";

        interner.setId(Utils.getCurrUser().getId());
        internerSer.update(interner);

        model.addAttribute("msg_updated", true);
        return "interner/profile";

    }

    @PostMapping("admin-profile")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String postAdminProfile(@ModelAttribute @Valid Employee employee,
                                   BindingResult bindingResult,
                                   Model model) {

        if (bindingResult.hasErrors())
            return "employee/profile";

        employee.setId(Utils.getCurrUser().getId());
        employeeSer.update(employee);

        model.addAttribute("msg_updated", true);
        return "employee/profile";

    }


//    @PostMapping("admin-profile")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String postAdminProfile(@ModelAttribute @Valid Employee employee,
//                              BindingResult bindingResult,
//                              RedirectAttributes attr,
//                              Model model) {
//        model.addAttribute("isProfile", true);
//        if (bindingResult.hasErrors()) {
//            attr.addFlashAttribute("org.springframework.validation.BindingResult.employee", bindingResult);
//            attr.addFlashAttribute("employee",employee);
//            return "redirect:/profile";
//        }
//
//        employee.setId(Utils.getCurrUser().getId());
//        employeeSer.update(employee);
//
//        model.addAttribute("msg_updated", true);
//        return "employee/update";
//    }


}
