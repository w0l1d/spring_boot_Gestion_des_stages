package com.storactive.stg.controller;

import com.storactive.stg.model.*;
import com.storactive.stg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;

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
    public InternshipsController(StageService stageSer,
                                 TaskService taskSer,
                                 AbsenceService absenceSer,
                                 PieceService pieceSer,
                                 StagePieceService stagePieceSer,
                                 InternerService internerSer) {
        this.stageSer = stageSer;
        this.taskSer = taskSer;
        this.pieceSer = pieceSer;
        this.stagePieceSer = stagePieceSer;
        this.absenceSer = absenceSer;
        this.internerSer = internerSer;
    }

    @GetMapping({"/", ""})
    public String getIndex(Principal principal,
                           Model model,
                           HttpServletRequest request) {
        if (!request.isUserInRole("ROLE_ADMIN"))
            model.addAttribute("internships", internerSer.getUserInternships((Interner) principal));
        else
            model.addAttribute("internships", stageSer.getAll());
        if (request.getParameter("inserted") != null)
            model.addAttribute("msg_inserted", true);
        model.addAttribute("internship", new Internship());
        return "internship/index";
    }



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
                                      Model model,
                                      BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "internship/update";

        internship.setId(id);
        stageSer.update(internship);
        model.addAttribute("internships", stageSer.getAll())
                .addAttribute("msg_updated", true);
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
     * *********************
     * start  Tasks
     */


    @GetMapping("/{id}/tasks")
    public String getInternshipTasks(@PathVariable Integer id,
                                     Model model,
                                     HttpServletRequest request,
                                     Principal principal) {
        Internship internship = stageSer.findById(id);

        if (!request.isUserInRole("ROLE_ADMIN")
                && !isAuthorizedToInternship(internship.getInterner(), principal))
            throw new AuthorizationServiceException("Not Authorized to this Resource");


        model.addAttribute("internship", internship)
                .addAttribute("task", new Task())
                .addAttribute("tasks", internship.getTasks());
        return "internship/tasks";
    }


    @PostMapping("/{id}/tasks")
    @javax.transaction.Transactional
    public String postInternshipTask(@PathVariable Integer id,
                                     @ModelAttribute @Valid Task task,
                                     BindingResult bindingResult,
                                     Model model,
                                     HttpServletRequest request,
                                     Principal principal) {
        Internship internship = stageSer.findById(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("internship", internship)
                    .addAttribute("tasks", internship.getTasks());
            return "internship/tasks";
        }


        if (!request.isUserInRole("ROLE_ADMIN")
                && !isAuthorizedToInternship(internship.getInterner(), principal))
            throw new AuthorizationServiceException("Not Authorized to this Resource");

        task.setInternship(internship);
        task = taskSer.create(task);
        internship.getTasks().add(task);

        model.addAttribute("internship", internship);
        model.addAttribute("task", new Task());
        model.addAttribute("tasks", internship.getTasks());
        model.addAttribute("msg_inserted", true);
        return "internship/tasks";
    }


    @GetMapping("/{id}/tasks/{task_id}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteInternshipTask(@PathVariable("id") Integer id,
                                       @PathVariable("task_id") Integer taskId,
                                       Model model) {
        taskSer.delete(taskId);
        model.addAttribute("msg_deleted", true);
        return "redirect:/internships/" + id + "/tasks?deleted";
    }


    /*
     *  end  Tasks
     * *****************
     */


    /*
     **********************
     *  start  Absences
     */
    @GetMapping("/{id}/absences")
    public String getInternshipAbsences(@PathVariable Integer id,
                                        Model model,
                                        HttpServletRequest request,
                                        Principal principal) {
        Internship internship = stageSer.findById(id);

        if (!request.isUserInRole("ROLE_ADMIN")
                && !isAuthorizedToInternship(internship.getInterner(), principal))
            throw new AuthorizationServiceException("Not Authorized to this Resource");

        model.addAttribute("internship", internship);
        model.addAttribute("absence", new Absence());
        model.addAttribute("absences", internship.getAbsences());
        return "internship/absences";
    }


    @PostMapping("/{id}/absences")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public String postInternshipAbsence(@PathVariable Integer id,
                                        @ModelAttribute @Valid Absence absence,
                                        BindingResult bindingResult,
                                        Model model) {
        Internship internship = stageSer.findById(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("internship", internship)
                    .addAttribute("absences", internship.getAbsences());
            return "internship/absences";
        }

        absence.setInternship(internship);
        absence = absenceSer.create(absence);
        internship.getAbsences().add(absence);

        model.addAttribute("internship", internship)
                .addAttribute("absence", new Absence())
                .addAttribute("absences", internship.getAbsences())
                .addAttribute("msg_inserted", true);
        return "internship/absences";
    }


    @GetMapping("/{id}/absences/{absence_id}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteAbsence(@PathVariable("id") Integer id,
                                @PathVariable("absence_id") Integer absId,
                                Model model,
                                HttpServletRequest request,
                                Principal principal) {
        Internship internship = stageSer.findById(id);

        if (!request.isUserInRole("ROLE_ADMIN")
                && !isAuthorizedToInternship(internship.getInterner(), principal))
            throw new AuthorizationServiceException("Not Authorized to this Resource");

        absenceSer.delete(absId);
        model.addAttribute("msg_deleted", true);
        return "redirect:/internships/" + id + "/absences?deleted";
    }

    /*
     *  end  Absences
     * ******************
     */


    /*
     * ********************
     * start  Files
     */

    @GetMapping("/{id}/files")
    public String getInternshipFiles(@PathVariable Integer id,
                                     Model model,
                                     HttpServletRequest request,
                                     Principal principal) {
        Internship internship = stageSer.findById(id);

        if (!request.isUserInRole("ROLE_ADMIN")
                && !isAuthorizedToInternship(internship.getInterner(), principal))
            throw new AuthorizationServiceException("Not Authorized to this Resource");

        model.addAttribute("internship", internship);
        model.addAttribute("pieces", pieceSer.getAll());
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

//        //if any Validation Errors Occurs
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("internship", internship)
//                    .addAttribute("files", internship.getStagePieces());
//
//            return "internship/files";
//        }

        Piece piece = pieceSer.findById(pieceId);

        stagePieceSer.create(piece, internship, file);

        model.addAttribute("internship", internship)
                .addAttribute("files", internship.getStagePieces())
                .addAttribute("msg_inserted", true);
        return "internship/files";
    }


    /*
     *  end  Files
     * ******************
     */


    private boolean isAuthorizedToInternship(Interner owner, Principal principal) {
        Interner loggedInterner = (Interner) principal;
        return owner.getId().equals(loggedInterner.getId());
    }
}
