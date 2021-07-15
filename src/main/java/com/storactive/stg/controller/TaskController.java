package com.storactive.stg.controller;

import com.storactive.stg.Utils;
import com.storactive.stg.model.Internship;
import com.storactive.stg.model.Task;
import com.storactive.stg.service.InternerService;
import com.storactive.stg.service.StageService;
import com.storactive.stg.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    final TaskService taskSer;
    final InternerService internerSer;
    final StageService stageSer;


    @GetMapping({"/", ""})
    public String getIndex(Model model,
                           HttpServletRequest request) {

        model.addAttribute("task", new Task());
        return "task/index";
    }

    @PostMapping({"/", ""})
    public String postTask(Model model,
                           HttpServletRequest request,
                           @ModelAttribute @Valid Task task,
                           BindingResult bindingResult,
                           @NotNull @RequestParam("internship-select") Integer internshipId) {
        if (bindingResult.hasErrors())
            return "task/index";

        Internship internship = stageSer.findById(internshipId);

        Utils.assertAuthorizedToResource(request, internship.getInterner());

        task.setInternship(internship);
        task = taskSer.create(task);
        internship.getTasks().add(task);

        return "redirect:/tasks?inserted";
    }


    @GetMapping("/{id}/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getUpdateTask(@PathVariable Integer id, Model model) {
        Task task = taskSer.findById(id);
        model.addAttribute("task", task);
        return "task/update";
    }


    @PostMapping("/{id}/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String putUpdateTask(@NotNull @Positive @PathVariable Integer id,
                                @ModelAttribute @Valid Task task,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors())
            return "task/update";

        task.setId(id);
        taskSer.update(task);
        model.addAttribute("msg_updated", true);
        return "task/index";
    }

    @GetMapping("/{id}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteTask(@PathVariable Integer id,
                                 Model model) {
        taskSer.delete(id);
        model.addAttribute("msg_deleted", true);
        return "redirect:/tasks?deleted";
    }


}
