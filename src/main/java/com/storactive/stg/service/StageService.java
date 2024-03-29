package com.storactive.stg.service;

import com.storactive.stg.Utils;
import com.storactive.stg.model.Interner;
import com.storactive.stg.model.Internship;
import com.storactive.stg.repository.StageRepo;
import com.storactive.stg.specs.InternerOwnSpec;
import com.storactive.stg.specs.InternshipContainSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class StageService {

    @Value("${spring.custom.select2.page-size}")
    private Integer MAX_PAGE_SIZE;

    @Value("${spring.custom.select2.limited-results-number}")
    private boolean LIMITED_NUMBER;

    final String OBJ = "Stage";

    final StageRepo stageRepo;
    final HistoryService historySer;
    final AlertService alertSer;


    public long countAll(Interner interner) {
        if (Objects.isNull(interner))
            return stageRepo.count();
        return stageRepo.count(InternerOwnSpec.getInternshipSpec(interner));
    }

    public long countAllActive(Interner interner) {
        if (Objects.isNull(interner))
            return stageRepo.countActiveInternship();
        return stageRepo.countActiveInternship(interner.getId());
    }


    public Page<Internship> findAllContains(String s, boolean isInterner) {
        Pageable pageable = (LIMITED_NUMBER) ?
                Pageable.ofSize(MAX_PAGE_SIZE)
                :
                Pageable.unpaged();
        if (isInterner)
            return stageRepo.findAll(Specification
                            .where(InternshipContainSpec.getInternshipSpec(s))
                            .and(InternerOwnSpec.getInternshipSpec((Interner) Utils.getCurrUser())),
                    pageable);
        return stageRepo.findAll(InternshipContainSpec.getInternshipSpec(s), pageable);

    }


    public Internship create(Internship internship) {
        internship.setId(null);
        Internship internship1 = stageRepo.save(internship);

        historySer.objetCreated(OBJ, internship1.getId());

        return internship1;
    }

    public Internship update(final Internship internship) {
        Internship oldInternship = findById(internship.getId());
        internship.setInterner(oldInternship.getInterner());
        int oldSts = oldInternship.getStatus(),
                newSts = internship.getStatus();

        stageRepo.save(internship);

        historySer.objetUpdated(OBJ, internship.getId());
        alertSer.alertUpdateInternship(internship, oldSts, newSts);

        return internship;
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
