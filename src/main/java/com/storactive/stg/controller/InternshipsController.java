package com.storactive.stg.controller;

import com.storactive.stg.Utils;
import com.storactive.stg.model.*;
import com.storactive.stg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/internships")
public class InternshipsController {

    final StageService stageSer;
    final StagePieceService stagePieceSer;
    final InternerService internerSer;
    final TaskService taskSer;
    final PieceService pieceSer;
    final AbsenceService absenceSer;

    @Autowired
    public InternshipsController(StageService stageSer, TaskService taskSer,
                                 AbsenceService absenceSer, StagePieceService stagePieceSer,
                                 PieceService pieceSer, InternerService internerSer) {
        this.stageSer = stageSer;
        this.taskSer = taskSer;
        this.pieceSer = pieceSer;
        this.stagePieceSer = stagePieceSer;
        this.absenceSer = absenceSer;
        this.internerSer = internerSer;
    }

    @GetMapping({"/", ""})
    public String getIndex(Model model,
                           HttpServletRequest request) {


        if (request.getParameter("inserted") != null)
            model.addAttribute("msg_inserted", true);
        model.addAttribute("internship", new Internship());
        return "internship/index";
    }


    @PostMapping({"/", ""})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String postInternship(@ModelAttribute @Valid Internship internship,
                                 @NotNull @RequestParam("interner-select") Integer internerId,
                                 BindingResult result,
                                 Model model) {
        if (result.hasErrors())
            return "internship/index";
        Interner interner = internerSer.findById(internerId);

        internship.setInterner(interner);
        internship = stageSer.create(internship);

        interner.getInternships().add(internship);

        return "internship/index";
    }

    @GetMapping("/{id}")
    public String getInternship(@PathVariable Integer id,
                                HttpServletRequest request,
                                Model model) {
        Internship internship = stageSer.findById(id);

        Utils.assertAuthorizedToResource(request, internship.getInterner());


        model.addAttribute("internship", internship)
                .addAttribute("interner", internship.getInterner());

        if (model.getAttribute("task") == null)
            model.addAttribute("task", new Task());
        if (model.getAttribute("absence") == null)
            model.addAttribute("absence", new Absence());

        model.addAttribute("pieces", pieceSer.findAll());

        return "internship/view";
    }


    @GetMapping({"/{id}/update"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getUpdateInternship(@PathVariable Integer id, Model model) {
        Internship internship = stageSer.findById(id);
        model.addAttribute("internship", internship);
        return "internship/update";
    }

    @PostMapping("/{id}/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String putUpdateInternship(@PathVariable Integer id,
                                      @ModelAttribute @Valid Internship internship,
                                      Model model,
                                      BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "internship/update";

        internship.setId(id);
        stageSer.update(internship);
        model.addAttribute("msg_updated", true);
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




    /*
     **********************         Start  Tasks         **********************
     */


    @GetMapping("/{id}/tasks")
    public String getInternshipTasks(@PathVariable Integer id,
                                     Model model,
                                     HttpServletRequest request) {
        Internship internship = stageSer.findById(id);

        Utils.assertAuthorizedToResource(request, internship.getInterner());

        model.addAttribute("internship", internship)
                .addAttribute("task", new Task())
                .addAttribute("tasks", internship.getTasks());
        return "internship/tasks";
    }


    @PostMapping("/{id}/tasks")
    @Transactional
    public String postInternshipTask(@PathVariable Integer id,
                                     @ModelAttribute @Valid Task task,
                                     BindingResult bindingResult,
                                     Model model,
                                     HttpServletRequest request) {
        Internship internship = stageSer.findById(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("internship", internship)
                    .addAttribute("tasks", internship.getTasks());
            return "internship/view";
        }

        // Interner can add task only to it's own internships
        // while admin can do it to any
        Utils.assertAuthorizedToResource(request, internship.getInterner());

        task.setInternship(internship);
        taskSer.create(task);

        model.addAttribute("msg_inserted", true);
        return "redirect:/internships/" + id;
    }


    @GetMapping("/{id}/tasks/{task_id}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteInternshipTask(@PathVariable("id") Integer id,
                                       @PathVariable("task_id") Integer taskId,
                                       Model model) {
        taskSer.delete(taskId);
        model.addAttribute("msg_deleted", true);
        return "redirect:/internships/" + id + "?deleted";
    }

    /*
     **********************         End  Tasks         **********************
     */


    /*
     **********************         Start  Absences         **********************
     */
    @GetMapping("/{id}/absences")
    public String getInternshipAbsences(@PathVariable Integer id,
                                        Model model,
                                        HttpServletRequest request) {
        Internship internship = stageSer.findById(id);

        Utils.assertAuthorizedToResource(request, internship.getInterner());


        model.addAttribute("internship", internship);
        model.addAttribute("absence", new Absence());
        model.addAttribute("absences", internship.getAbsences());
        return "internship/absences";
    }


    @PostMapping("/{id}/absences")
    @Transactional
    public String postInternshipAbsence(@PathVariable Integer id,
                                        @ModelAttribute @Valid Absence absence,
                                        HttpServletRequest request,
                                        BindingResult bindingResult,
                                        Model model) {
        Internship internship = stageSer.findById(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("internship", internship);
            return "internship/view";
        }

        Utils.assertAuthorizedToResource(request, internship.getInterner());

        absence.setInternship(internship);
        absence = absenceSer.create(absence);
        internship.getAbsences().add(absence);

        model.addAttribute("msg_inserted", true);
        return "redirect:/internships/" + id;
    }


    @GetMapping("/{id}/absences/{absence_id}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteAbsence(@PathVariable("id") Integer id,
                                @PathVariable("absence_id") Integer absId,
                                Model model,
                                HttpServletRequest request) {
        Internship internship = stageSer.findById(id);

        absenceSer.delete(absId);
        model.addAttribute("msg_deleted", true);
        return "redirect:/internships/" + id + "?deleted";
    }
    /*
     **********************         End  Absences         **********************
     */



    /*
     **********************         Start  Files         **********************
     */

    @GetMapping("/{id}/files")
    public String getInternshipFiles(@PathVariable Integer id,
                                     Model model,
                                     HttpServletRequest request) {
        Internship internship = stageSer.findById(id);


        Utils.assertAuthorizedToResource(request, internship.getInterner());

        model.addAttribute("internship", internship);
        model.addAttribute("file", new StagePiece());
        model.addAttribute("files", internship.getStagePieces());
        return "internship/files";
    }


    @PostMapping("/{id}/files")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public String postInternshipFile(@PathVariable Integer id,
                                     @RequestParam("piece") Integer pieceId,
                                     @RequestParam("file") MultipartFile file,
                                     Model model) {
        Internship internship = stageSer.findById(id);


        Piece piece = pieceSer.findById(pieceId);
        stagePieceSer.create(piece, internship, file);

        model.addAttribute("msg_inserted", true);

        return "redirect:/internships/" + id;
    }


    /*
     **********************         End  Files         **********************
     */


}
