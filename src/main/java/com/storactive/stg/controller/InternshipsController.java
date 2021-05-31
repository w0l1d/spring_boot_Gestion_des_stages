package com.storactive.stg.controller;

import com.storactive.stg.model.Internship;
import com.storactive.stg.model.Task;
import com.storactive.stg.service.StageService;
import com.storactive.stg.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/internships")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class InternshipsController {

    final StageService stageSer;
    final TaskService taskSer;

    @Autowired
    public InternshipsController(StageService stageSer, TaskService taskSer) {
        this.stageSer = stageSer;
        this.taskSer = taskSer;
    }

    @GetMapping({"/", ""})
    public String getIndex(Model model) {
        model.addAttribute("internship", new Internship());
        model.addAttribute("internships", stageSer.getAll());
        return "internship/index";
    }


    @PostMapping({"/", ""})
    public String postAddInternship(@ModelAttribute @Valid Internship internship, Model model) {
        stageSer.create(internship);

        model.addAttribute("internships", stageSer.getAll());
        return "internship/index";
    }


    @GetMapping({"/{id}"})
    public String getUpdateInternship(@PathVariable Integer id, Model model) {
        Internship internship = stageSer.findById(id);
        model.addAttribute("internship", internship);
        return "internship/update";
    }

    @PostMapping({"/{id}"})
    public String putUpdateInternship(@PathVariable Integer id,
                                     @ModelAttribute @Valid Internship internship,
                                     Model model) {
        internship.setId(id);
        stageSer.update(internship);
        model.addAttribute("msg_updated", true);
        model.addAttribute("internships", stageSer.getAll());
        return "internship/index";
    }

    @GetMapping("/{id}/delete")
    public String deleteInternship(@PathVariable Integer id,
                                   Model model) {
        stageSer.delete(id);
        model.addAttribute("msg_deleted", true);
        return "redirect:/internships?deleted";
    }

    @GetMapping("/{id}/tasks")
    public String getInternshipTasks(@PathVariable Integer id,
                                     Model model) {
        Internship internship = stageSer.findById(id);
        model.addAttribute("internship", internship);
        model.addAttribute("task", new Task());
        model.addAttribute("tasks", internship.getTasks());
        return "internship/tasks";
    }


    @PostMapping("/{id}/tasks")
    public String postInternshipTasks(@PathVariable Integer id,
                                      @ModelAttribute @Valid Task task,
                                      Model model) {
        Internship internship = stageSer.findById(id);
        task.setInternship(internship);
        task = taskSer.create(task);
        internship.getTasks().add(task);
        internship = stageSer.update(internship);
        model.addAttribute("internship", internship);
        model.addAttribute("task", new Task());
        model.addAttribute("tasks", internship.getTasks());
        model.addAttribute("msg_inserted", true);
        return "internship/tasks";
    }


    @GetMapping("/{id}/tasks/{task_id}/delete")
    public String deleteTask(@PathVariable("id") Integer id,
                             @PathVariable("task_id") Integer taskId,
                             Model model) {
        taskSer.delete(taskId);
        model.addAttribute("msg_deleted", true);
        return "redirect:/internships/" + id + "/tasks?deleted";
    }


}
