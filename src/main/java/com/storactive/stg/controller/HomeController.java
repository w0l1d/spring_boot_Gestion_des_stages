package com.storactive.stg.controller;

import com.storactive.stg.Utils;
import com.storactive.stg.model.Interner;
import com.storactive.stg.model.User;
import com.storactive.stg.service.EmployeeService;
import com.storactive.stg.service.InternerService;
import com.storactive.stg.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    final BCryptPasswordEncoder encoder;
    final EmployeeService userSer;
    final InternerService internerSer;
    final EmployeeService employeeSer;
    final StageService stageSer;


    @GetMapping({"", "home"})
    public String home(Model model,
                       HttpServletRequest request) {
        User currUser = Utils.getCurrUser();
        if (!Utils.isAuthenticated())
            return "redirect:/login";


        if (request.isUserInRole("ROLE_ADMIN"))
            model.addAttribute("countInterners", internerSer.countAll())
                    .addAttribute("countActiveInterners", internerSer.countAllActive())
                    .addAttribute("countEmployees", employeeSer.count())
                    .addAttribute("countInternships", stageSer.countAll(null))
                    .addAttribute("countActiveInternships", stageSer.countAllActive(null));
        else
            model.addAttribute("countInternships", stageSer.countAll((Interner) currUser))
                    .addAttribute("countActiveInternships", stageSer.countAllActive((Interner) currUser));

        return "index_Dashboard";

    }


    @GetMapping("logout")
    public String getLogout(HttpServletRequest request)
            throws ServletException {
        request.logout();
        return "redirect:/login?logout";
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
