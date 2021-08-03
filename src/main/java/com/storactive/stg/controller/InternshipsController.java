package com.storactive.stg.controller;

import com.storactive.stg.Utils;
import com.storactive.stg.model.*;
import com.storactive.stg.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/internships")
@RequiredArgsConstructor
public class InternshipsController {

    final StageService stageSer;
    final StagePieceService stagePieceSer;
    final InternerService internerSer;
    final TaskService taskSer;
    final PieceService pieceSer;
    final AbsenceService absenceSer;



    @GetMapping({"/", ""})
    public String getIndex(Model model,
                           HttpServletRequest request) {


        if (request.getParameter("inserted") != null)
            model.addAttribute("msg_inserted", true);

        if (model.getAttribute("msg_updated") != null) {
            model.addAttribute("msg_updated", true);
            System.out.println("******update is in model");

        }

        model.addAttribute("internship", new Internship());
        return "internship/index";
    }


    @PostMapping({"/", ""})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String postInternship(@NotNull @RequestParam("interner-select") Integer internerId,
                                 @ModelAttribute @Valid Internship internship,
                                 BindingResult result,
                                 Model model) {
        if (result.hasErrors())
            return "internship/index";

        Interner interner = internerSer.findById(internerId);

        internship.setInterner(interner);
        internship = stageSer.create(internship);

        interner.getInternships().add(internship);

        model.addAttribute("msg_inserted", true);

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
                                      BindingResult bindingResult,
                                      Model model) {
        if (bindingResult.hasErrors())
            return "internship/update";


        internship.setId(id);
        stageSer.update(internship);
        model.addAttribute("msg_updated", true);
        return "redirect:/internships?updated";
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
                                     RedirectAttributes attr,
                                     Model model,
                                     HttpServletRequest request) {
        Internship internship = stageSer.findById(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("internship", internship);
            attr.addFlashAttribute("internship", internship);
            attr.addFlashAttribute("org.springframework.validation.BindingResult.absence", bindingResult);

            if (model.containsAttribute("task"))
                model.addAttribute("task", new Task());
            if (model.containsAttribute("absence"))
                model.addAttribute("absence", new Absence());
            return "redirect:/internships/" + id;
        }

        // Interner can add task only to it's own internships
        // while admin can do it to any
        Utils.assertAuthorizedToResource(request, internship.getInterner());

        task.setInternship(internship);
        taskSer.create(task);

        model.addAttribute("task_inserted", true);
        return "redirect:/internships/" + id + "?task_inserted";
    }


    @GetMapping("/{id}/tasks/{task_id}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteInternshipTask(@PathVariable("id") Integer id,
                                       @PathVariable("task_id") Integer taskId,
                                       Model model) {
        taskSer.delete(taskId);
        model.addAttribute("task_deleted", true);
        return "redirect:/internships/" + id + "?task_deleted";
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

    @GetMapping("/{id}/absence/create")
    public String getAddInternshipAbsences(@PathVariable Integer id,
                                           Model model,
                                           HttpServletRequest request) {
        Internship internship = stageSer.findById(id);

        Utils.assertAuthorizedToResource(request, internship.getInterner());

        model.addAttribute("absence", new Absence());
        return "internship/add_absence_page";
    }


    @PostMapping("/{id}/absence/create")
    @Transactional
    public String postInternshipAbsence(@PathVariable Integer id,
                                        @ModelAttribute @Valid Absence absence,
                                        BindingResult bindingResult,
                                        HttpServletRequest request,
                                        RedirectAttributes attr) {
        Internship internship = stageSer.findById(id);

        System.out.println("************** 00000000");
        if (bindingResult.hasErrors())
            return "internship/add_absence_page";

        System.out.println("************** 111111");

        Utils.assertAuthorizedToResource(request, internship.getInterner());
        System.out.println("************** 22222222");
        absence.setInternship(internship);
        absenceSer.create(absence);

        attr.addFlashAttribute("absence_inserted", true);
        return "redirect:/internships/" + id + "?absence_inserted";
    }


    @GetMapping("/{id}/absences/{absence_id}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteAbsence(@PathVariable("id") Integer id,
                                @PathVariable("absence_id") Integer absId,
                                Model model) {
        Internship internship = stageSer.findById(id);

        absenceSer.delete(absId);
        model.addAttribute("absence_deleted", true);
        return "redirect:/internships/" + id + "?absence_deleted";
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

        return "redirect:/internships/" + id + "?doc_inserted";
    }

    @GetMapping("/{id}/files/{file_id}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteFile(@PathVariable("id") Integer id,
                             @PathVariable("file_id") Integer flId,
                             Model model) {
        Internship internship = stageSer.findById(id);

        stagePieceSer.delete(flId);
        model.addAttribute("absence_deleted", true);
        return "redirect:/internships/" + id + "?doc_deleted";
    }

    /*
     **********************         End  Files         **********************
     */


}
