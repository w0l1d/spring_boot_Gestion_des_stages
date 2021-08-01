package com.storactive.stg.controller;

import com.storactive.stg.Utils;
import com.storactive.stg.model.*;
import com.storactive.stg.service.DataTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class DataTableRestController {

    final DataTableService dataTableService;

    @Autowired
    public DataTableRestController(DataTableService employeeDtSer) {
        this.dataTableService = employeeDtSer;
    }


    @PostMapping("/employee")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public DataTablesOutput<Employee> getEmployees(@Valid @RequestBody DataTablesInput dtRequest) {
        return dataTableService.getEmployees(dtRequest);
    }

    @PostMapping("/interner")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public DataTablesOutput<Interner> getInterners(@Valid @RequestBody DataTablesInput dtRequest) {
        return dataTableService.getInterners(dtRequest);
    }

    @PostMapping("/internship")
    public DataTablesOutput<Internship> getInternships(@Valid @RequestBody DataTablesInput dtRequest,
                                                       HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN"))
            return dataTableService.getStages(dtRequest);

        return dataTableService.getStages(dtRequest, (Interner) Utils.getCurrUser());
    }

    @PostMapping("/curr-internship")
    public DataTablesOutput<Internship> getCurrInternships(@Valid @RequestBody DataTablesInput dtRequest,
                                                           HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN"))
            return dataTableService.getCurrStages(dtRequest);

        return dataTableService.getCurrStages(dtRequest, (Interner) Utils.getCurrUser());
    }


    @PostMapping("/history")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public DataTablesOutput<History> getHistory(@Valid @RequestBody DataTablesInput dtRequest) {
        return dataTableService.getHistory(dtRequest);
    }

    @PostMapping("/my-history")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public DataTablesOutput<History> getMyHistory(@Valid @RequestBody DataTablesInput dtRequest) {
        return dataTableService.getHistory(dtRequest, Utils.getCurrUser());
    }

    @PostMapping("/absence")
    public DataTablesOutput<Absence> getAbsences(@Valid @RequestBody DataTablesInput dtRequest,
                                                 HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN"))
            return dataTableService.getAbsences(dtRequest);

        return dataTableService.getAbsences(dtRequest, (Interner) Utils.getCurrUser());
    }

    @PostMapping("/task")
    public DataTablesOutput<Task> getTasks(@Valid @RequestBody DataTablesInput dtRequest,
                                           HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN"))
            return dataTableService.getTasks(dtRequest);

        return dataTableService.getTasks(dtRequest, (Interner) Utils.getCurrUser());
    }

    @PostMapping("/piece")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public DataTablesOutput<Piece> getPieces(@Valid @RequestBody DataTablesInput dtRequest) {
        return dataTableService.getPieces(dtRequest);
    }

    @PostMapping("/stagePiece")
    public DataTablesOutput<StagePiece> getStagePieces(@Valid @RequestBody DataTablesInput dtRequest,
                                                       HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN"))
            return dataTableService.getStagePieces(dtRequest);

        return dataTableService.getStagePieces(dtRequest, (Interner) Utils.getCurrUser());
    }


    @PostMapping("/alert")
    public DataTablesOutput<Alert> getAlerts(@Valid @RequestBody DataTablesInput dtRequest,
                                             HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN"))
            return dataTableService.getAlerts(dtRequest);

        return dataTableService.getAlerts(dtRequest, (Interner) Utils.getCurrUser());
    }

}
