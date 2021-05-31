package com.storactive.stg.controller;

import com.storactive.stg.model.Absence;
import com.storactive.stg.model.Internship;
import com.storactive.stg.model.Task;
import com.storactive.stg.service.AbsenceService;
import com.storactive.stg.service.StageService;
import com.storactive.stg.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/internships")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class InternshipsController {

    final StageService stageSer;
    final TaskService taskSer;
    final AbsenceService absenceSer;

    @Autowired
    public InternshipsController(StageService stageSer,
                                 TaskService taskSer,
                                 AbsenceService absenceSer) {
        this.stageSer = stageSer;
        this.taskSer = taskSer;
        this.absenceSer = absenceSer;
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


    @GetMapping("/{id}/absences")
    public String getInternshipAbsences(@PathVariable Integer id,
                                        Model model) {
        Internship internship = stageSer.findById(id);
        model.addAttribute("internship", internship);
        model.addAttribute("absence", new Absence());
        model.addAttribute("absences", internship.getAbsences());
        return "internship/absences";
    }


    @PostMapping("/{id}/absences")
    @Transactional
    public String postInternshipAbsences(@PathVariable Integer id,
                                         @ModelAttribute @Valid Absence absence,
                                         Model model) {
        Internship internship = stageSer.findById(id);
        absence.setInternship(internship);
        absence = absenceSer.create(absence);
        internship.getAbsences().add(absence);
        internship = stageSer.update(internship);
        model.addAttribute("internship", internship);
        model.addAttribute("absence", new Absence());
        model.addAttribute("absences", internship.getAbsences());
        model.addAttribute("msg_inserted", true);
        return "internship/absences";
    }


    @GetMapping("/{id}/absences/{absence_id}/delete")
    public String deleteAbsence(@PathVariable("id") Integer id,
                                @PathVariable("absence_id") Integer absId,
                                Model model) {
        absenceSer.delete(absId);
        model.addAttribute("msg_deleted", true);
        return "redirect:/internships/" + id + "/absences?deleted";
    }
}
