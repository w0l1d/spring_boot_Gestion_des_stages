package com.storactive.stg.service;

import com.storactive.stg.model.Internship;
import com.storactive.stg.repository.StageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StageService {

    final String OBJ = "Stage";

    final StageRepo stageRepo;
    final HistoryService historySer;

    @Autowired
    public StageService(StageRepo stageRepo, HistoryService historySer) {
        this.stageRepo = stageRepo;
        this.historySer = historySer;
    }


    public List<Internship> getAll() {
        return stageRepo.findAll();
    }

    public Internship create(Internship internship) {
        internship.setId(null);
        Internship internship1 = stageRepo.save(internship);

        historySer.objetCreated(OBJ, internship1.getId());

        return internship1;
    }

    public Internship update(Internship internship) {
        if (!stageRepo.existsById(internship.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Internship Not Found");
        Internship internship1 = stageRepo.save(internship);
        historySer.objetUpdated(OBJ, internship1.getId());
        return internship1;
    }

    public void delete(Integer id) {
        if (!stageRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Internship Not Found");
        stageRepo.deleteById(id);
        historySer.objetDeleted(OBJ, id);
    }

    public Internship findById(int id) {
        return stageRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Internship Not Found"));
    }


}
