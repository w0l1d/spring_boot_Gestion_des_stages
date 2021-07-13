package com.storactive.stg.controller;

import com.storactive.stg.model.Task;
import com.storactive.stg.service.InternerService;
import com.storactive.stg.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    final TaskService taskSer;
    final InternerService internerSer;

    @Autowired
    public TaskController(TaskService taskSer,
                          InternerService internerSer) {
        this.taskSer = taskSer;
        this.internerSer = internerSer;
    }


    @GetMapping({"/", ""})
    public String getIndex(Model model,
                           HttpServletRequest request) {
        List<Task> tasks;

//        if (!request.isUserInRole("ROLE_ADMIN")) {
//            Interner interner = (Interner) Utils.getCurrUser();
//            tasks = internerSer.getUserTasks(interner);
//        } else
//            tasks = taskSer.getAll();
//
//        model.addAttribute("tasks", tasks);

        model.addAttribute("task", new Task());
        return "task/index";
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
