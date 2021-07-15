package com.storactive.stg.controller;

import com.storactive.stg.service.InternerService;
import com.storactive.stg.service.PieceService;
import com.storactive.stg.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AutoCompleteRestController {

    final InternerService internerSer;
    final StageService stageSer;
    final PieceService pieceSer;

    @GetMapping("/interner/ac")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getInterners(@RequestParam(value = "q", required = false) String query) {
        return ResponseEntity.ok(internerSer.findAllContains(query));
    }

    @GetMapping("/internship/ac")
    public ResponseEntity<?> getInternships(@RequestParam(value = "q", required = false) String query,
                                            HttpServletRequest request) {
        return ResponseEntity.ok(stageSer.findAllContains(query, request.isUserInRole("ROLE_INTERNER")));
    }


}
