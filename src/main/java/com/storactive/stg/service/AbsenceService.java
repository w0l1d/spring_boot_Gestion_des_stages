package com.storactive.stg.service;

import com.storactive.stg.model.Absence;
import com.storactive.stg.model.Internship;
import com.storactive.stg.repository.AbsenceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AbsenceService {

    final AbsenceRepo absenceRepo;
    final StageService internshipSer;

    @Autowired
    public AbsenceService(AbsenceRepo absenceRepo, StageService internshipSer) {
        this.absenceRepo = absenceRepo;
        this.internshipSer = internshipSer;
    }

    public List<Absence> getAll() {
        return absenceRepo.findAll();
    }


    public Absence create(Absence absence) {
        absence.setId(null);
        return absenceRepo.save(absence);
    }

    @Transactional
    public Absence update(Absence absence) {
        Absence absence1 = findById(absence.getId());
        absence.setInternship(absence1.getInternship());
        return absenceRepo.save(absence);
    }

    @Transactional
    public void delete(Integer id) {
        Absence absence = findById(id);
        Internship internship = absence.getInternship();
        internship.getAbsences().remove(absence);
        internshipSer.update(internship);
        absenceRepo.deleteById(id);
    }


    public Absence findById(int id) {
        return absenceRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Absence Not Found"));
    }


}
