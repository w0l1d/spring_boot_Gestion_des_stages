package com.storactive.stg.controller;

import com.storactive.stg.model.Task;
import com.storactive.stg.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    final TaskService taskSer;

    @Autowired
    public TaskController(TaskService taskSer) {
        this.taskSer = taskSer;
    }


    @GetMapping({"/", ""})
    public String getIndex(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("tasks", taskSer.getAll());
        return "task/index";
    }


    @PostMapping({"/", ""})
    public String postAddTask(@ModelAttribute @Valid Task task, Model model) {
        taskSer.create(task);

        model.addAttribute("tasks", taskSer.getAll());
        return "task/index";
    }


    @GetMapping({"/{id}"})
    public String getUpdateTask(@PathVariable Integer id, Model model) {
        Task task = taskSer.findById(id);
        model.addAttribute("task", task);
        return "task/update";
    }


    @PostMapping({"/{id}"})
    public String putUpdateTask(@NotNull @Positive @PathVariable Integer id,
                                    @ModelAttribute @Valid Task task,
                                    Model model) {
        task.setId(id);
        taskSer.update(task);

        model.addAttribute("msg_updated", true);
        model.addAttribute("tasks", taskSer.getAll());
        return "task/index";
    }

    @GetMapping({"/{id}/delete"})
    public String deleteTask(@PathVariable Integer id,
                                 Model model) {
        taskSer.delete(id);
        model.addAttribute("msg_deleted", true);
        return "redirect:/task?deleted";
    }

}
