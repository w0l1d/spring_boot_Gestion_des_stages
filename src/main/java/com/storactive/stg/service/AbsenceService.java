package com.storactive.stg.service;

import com.storactive.stg.model.Absence;
import com.storactive.stg.model.Internship;
import com.storactive.stg.repository.AbsenceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AbsenceService {

    final String OBJ = "Absence";
    final AbsenceRepo absenceRepo;
    final StageService internshipSer;
    final HistoryService historySer;

    @Autowired
    public AbsenceService(AbsenceRepo absenceRepo, StageService internshipSer, HistoryService historySer) {
        this.absenceRepo = absenceRepo;
        this.internshipSer = internshipSer;
        this.historySer = historySer;
    }


    public Absence create(Absence absence) {
        absence.setId(null);
        Absence absence1 = absenceRepo.save(absence);
        historySer.objetCreated(OBJ, absence1.getId());
        return absence1;
    }

    @Transactional
    public Absence update(Absence absence) {
        Absence absence1 = findById(absence.getId());
        absence.setInternship(absence1.getInternship());
        Absence absence2 = absenceRepo.save(absence);
        historySer.objetUpdated(OBJ, absence2.getId());
        return absence2;
    }

    @Transactional
    public void delete(Integer id) {
        Absence absence = findById(id);
        Internship internship = absence.getInternship();
        internship.getAbsences().remove(absence);
        internshipSer.update(internship);
        absenceRepo.deleteById(id);
        historySer.objetDeleted(OBJ, id);
    }


    public Absence findById(int id) {
        return absenceRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Absence Not Found"));
    }


}
