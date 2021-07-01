package com.storactive.stg.controller;

import com.storactive.stg.model.Interner;
import com.storactive.stg.model.Piece;
import com.storactive.stg.model.StagePiece;
import com.storactive.stg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/documents")
public class DocumentController {


    final StageService stageSer;
    final FileStorageService storageSer;
    final PieceService pieceSer;
    final InternerService internerSer;
    final StagePieceService stagePieceSer;

    @Autowired
    public DocumentController(StageService stageSer,
                              FileStorageService storageSer,
                              StagePieceService stagePieceSer,
                              InternerService internerSer,
                              PieceService pieceSer) {
        this.stageSer = stageSer;
        this.storageSer = storageSer;
        this.stagePieceSer = stagePieceSer;
        this.internerSer = internerSer;
        this.pieceSer = pieceSer;
    }


    @GetMapping({"/", ""})
    public String getIndex(Model model,
                           Principal principal,
                           HttpServletRequest request) {

        model.addAttribute("files", getStagePieces(request, principal));
        model.addAttribute("file", new StagePiece());
        return "attachment/index";
    }


//    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String getUpdateStagePiece(@NotNull @Positive @PathVariable Integer id,
//                                   Model model) {
//        StagePiece stagePiece = stagePieceSer.findById(id);
//        model.addAttribute("stagePiece", stagePiece);
//        return "attachment/update";
//    }
//
//
//    @PostMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String putUpdateStagePiece(@NotNull @Positive @PathVariable Integer id,
//                                      @NotNull @RequestParam("file") MultipartFile file,
//                                      Principal principal,
//                                      HttpServletRequest request,
//                                      Model model) {
//        stagePieceSer.update(id, file);
//
//        model.addAttribute("msg_updated", true);
//        model.addAttribute("stagePiece", new StagePiece());
//        model.addAttribute("files", getStagePieces(request, principal.getName()));
//        return "attachment/index";
//    }

    @GetMapping("/{id}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteStagePiece(@PathVariable Integer id,
                                   Model model) {
        stagePieceSer.delete(id);
        model.addAttribute("msg_deleted", true);
        return "redirect:/documents?deleted";
    }

    @GetMapping("/{id}/download")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Resource> downloadStagePiece(@PathVariable Integer id) throws IOException {
        StagePiece stagePiece = stagePieceSer.findById(id);
        Resource resource = storageSer.load(stagePiece.getAttachment().getPath());

        if (!resource.exists())
            throw new RuntimeException("File is not found");
        if (!resource.isReadable())
            throw new RuntimeException("File not Readable");
        if (!resource.isFile())
            throw new RuntimeException("Resource is not a file");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + stagePiece.getAttachment().getPieceJoint() + "\"")
                .contentType(MediaType.parseMediaType(stagePiece.getAttachment().getType()))
                .body(resource);
    }


    @GetMapping("/categories")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getCategories(Model model) {
        model.addAttribute("pieces", pieceSer.getAll());
        model.addAttribute("piece", new Piece());

        return "attachment/piecesCat";
    }

    @PostMapping("/categories")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String postCategory(@ModelAttribute @Valid Piece piece,
                               Model model,
                               BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            pieceSer.create(piece);
            model.addAttribute("piece", new Piece());
            model.addAttribute("msg_inserted", true);
        }
        model.addAttribute("pieces", pieceSer.getAll());
        return "attachment/piecesCat";
    }


    @GetMapping("/categories/{id}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCategorie(@PathVariable("id") int pieceId,
                                  Model model) {
        pieceSer.delete(pieceId);
        model.addAttribute("pieces", pieceSer.getAll());
        model.addAttribute("piece", new Piece());
        model.addAttribute("msg_deleted", true);
        return "redirect:/documents/categories?deleted";
    }


    private List<StagePiece> getStagePieces(HttpServletRequest request, Principal principal) {
        if (request.isUserInRole("ROLE_INTERNER"))
            return internerSer.getUserFiles((Interner) principal);
        return stagePieceSer.getAll();
    }
}
