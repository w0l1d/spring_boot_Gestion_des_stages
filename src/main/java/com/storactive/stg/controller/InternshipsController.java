package com.storactive.stg.controller;

import com.storactive.stg.model.Absence;
import com.storactive.stg.model.Internship;
import com.storactive.stg.model.Task;
import com.storactive.stg.service.AbsenceService;
import com.storactive.stg.service.InternerService;
import com.storactive.stg.service.StageService;
import com.storactive.stg.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/internships")
public class InternshipsController {

    final StageService stageSer;
    final InternerService internerSer;
    final TaskService taskSer;
    final AbsenceService absenceSer;

    @Autowired
    public InternshipsController(StageService stageSer,
                                 TaskService taskSer,
                                 AbsenceService absenceSer,
                                 InternerService internerSer) {
        this.stageSer = stageSer;
        this.taskSer = taskSer;
        this.absenceSer = absenceSer;
        this.internerSer = internerSer;
    }

    @GetMapping({"/", ""})
    public String getIndex(Principal principal,
                           Model model,
                           HttpServletRequest request) {
        if (request.isUserInRole("ROLE_INTERNER"))
            model.addAttribute("internships", internerSer.getUserInternships(principal.getName()));
        else
            model.addAttribute("internships", stageSer.getAll());
        if (request.getParameter("inserted") != null)
            model.addAttribute("msg_inserted", true);
        model.addAttribute("internship", new Internship());
        return "internship/index";
    }


//    @PostMapping({"/", ""})
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String postAddInternship(@ModelAttribute @Valid Internship internship, Model model) {
//        stageSer.create(internship);
//
//        model.addAttribute("internships", stageSer.getAll());
//        return "internship/index";
//    }


    @GetMapping({"/{id}"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getUpdateInternship(@PathVariable Integer id, Model model) {
        Internship internship = stageSer.findById(id);
        model.addAttribute("internship", internship);
        return "internship/update";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    public String postInternshipTask(@PathVariable Integer id,
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteAbsence(@PathVariable("id") Integer id,
                                @PathVariable("absence_id") Integer absId,
                                Model model) {
        absenceSer.delete(absId);
        model.addAttribute("msg_deleted", true);
        return "redirect:/internships/" + id + "/absences?deleted";
    }
}
