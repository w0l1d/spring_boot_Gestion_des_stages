package com.storactive.stg.service;

import com.storactive.stg.Utils;
import com.storactive.stg.model.Interner;
import com.storactive.stg.model.Internship;
import com.storactive.stg.repository.StageRepo;
import com.storactive.stg.specs.InternerOwnSpec;
import com.storactive.stg.specs.InternshipContainSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<Internship> findAllContains(String s, boolean isInterner) {
        if (isInterner)
            return stageRepo.findAll(Specification
                            .where(InternshipContainSpec.getInternshipSpec(s))
                            .and(InternerOwnSpec.getInternshipSpec((Interner) Utils.getCurrUser())
                            ),
                    Pageable.ofSize(8));
        return stageRepo.findAll(InternshipContainSpec.getInternshipSpec(s), Pageable.ofSize(8));

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


    public List<Internship> getUserInternships(Interner interner) {
        return stageRepo.findAllByInterner_Id(interner.getId());
    }
}
