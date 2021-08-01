package com.storactive.stg.service;

import com.storactive.stg.model.*;
import com.storactive.stg.repository.*;
import com.storactive.stg.specs.ActiveInternshipsSpec;
import com.storactive.stg.specs.InternerOwnSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataTableService {

    final EmployeeRepo employeeRepo;
    final InternerRepo internerRepo;
    final StageRepo stageRepo;
    final TaskRepo taskRepo;
    final AbsenceRepo absenceRepo;
    final HistoryRepo historyRepo;
    final PieceRepo pieceRepo;
    final StagePieceRepo stagePieceRepo;
    final AlertRepo alertRepo;



    public DataTablesOutput<Employee> getEmployees(DataTablesInput request) {
        return employeeRepo.findAll(request);
    }

    public DataTablesOutput<Interner> getInterners(DataTablesInput request) {
        return internerRepo.findAll(request);
    }

    public DataTablesOutput<Task> getTasks(DataTablesInput request) {
        return taskRepo.findAll(request);
    }


    public DataTablesOutput<Task> getTasks(DataTablesInput dtRequest, Interner interner) {
        DataTablesOutput<Task> output = taskRepo.findAll(dtRequest, InternerOwnSpec.getTaskSpec(interner));
        output.setRecordsTotal(output.getRecordsFiltered());
        return output;
    }

    public DataTablesOutput<Alert> getAlerts(DataTablesInput request) {
        return alertRepo.findAll(request);
    }


    public DataTablesOutput<Alert> getAlerts(DataTablesInput dtRequest, Interner interner) {
        DataTablesOutput<Alert> output = alertRepo.findAll(dtRequest, InternerOwnSpec.getAlertSpec(interner));
        output.setRecordsTotal(output.getRecordsFiltered());
        return output;
    }

    public DataTablesOutput<Absence> getAbsences(DataTablesInput request) {
        return absenceRepo.findAll(request);
    }

    public DataTablesOutput<Absence> getAbsences(DataTablesInput request, Interner interner) {
        return absenceRepo.findAll(request, InternerOwnSpec.getAbsenceSpec(interner));
    }

    public DataTablesOutput<History> getHistory(DataTablesInput request) {
        return historyRepo.findAll(request);
    }

    public DataTablesOutput<History> getHistory(DataTablesInput request, User user) {
        return historyRepo.findAll(request, InternerOwnSpec.getHistorySpec(user));
    }

    public DataTablesOutput<Piece> getPieces(DataTablesInput request) {
        return pieceRepo.findAll(request);
    }

    public DataTablesOutput<StagePiece> getStagePieces(DataTablesInput request) {
        return stagePieceRepo.findAll(request);
    }

    public DataTablesOutput<StagePiece> getStagePieces(DataTablesInput request, Interner interner) {
        return stagePieceRepo.findAll(request, InternerOwnSpec.getDocSpec(interner));
    }

    public DataTablesOutput<Internship> getStages(DataTablesInput request) {
        return stageRepo.findAll(request);
    }

    public DataTablesOutput<Internship> getStages(DataTablesInput request, Interner interner) {
        return stageRepo.findAll(request, InternerOwnSpec.getInternshipSpec(interner));
    }


    public DataTablesOutput<Internship> getCurrStages(DataTablesInput request) {
        return stageRepo.findAll(request, ActiveInternshipsSpec.getCurrInternshipsSpec());
    }


    public DataTablesOutput<Internship> getCurrStages(DataTablesInput request, Interner interner) {
        return stageRepo.findAll(request, ActiveInternshipsSpec.getOwnedCurrInternshipsSpec(interner));
    }


}
