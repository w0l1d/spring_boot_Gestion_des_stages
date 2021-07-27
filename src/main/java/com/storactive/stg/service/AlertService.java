package com.storactive.stg.service;

import com.storactive.stg.Utils;
import com.storactive.stg.model.Alert;
import com.storactive.stg.model.Alert_;
import com.storactive.stg.model.Interner;
import com.storactive.stg.model.Internship;
import com.storactive.stg.model.enums.InternshipStatus;
import com.storactive.stg.repository.AlertRepo;
import com.storactive.stg.specs.InternerOwnSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AlertService {

    final AlertRepo alertRepo;

    public Page<Alert> getIndex() {
        return alertRepo.findAll(PageRequest.ofSize(5));
    }

    public Page<Alert> getIndexInterner() {
        return alertRepo.findAll(
                InternerOwnSpec.getAlertSpec((Interner) Utils.getCurrUser()),
                PageRequest.ofSize(5)
        );
    }


    public long countByStatus(short st) {
        return alertRepo.countByStatusEquals(st);
    }

    public long countByStatusForInterner(short st) {
        return alertRepo.count(
                InternerOwnSpec.getAlertSpec((Interner) Utils.getCurrUser())
                        .and((Specification<Alert>) (root, cq, cb)
                                -> cb.equal(root.get(Alert_.STATUS), st))
        );
    }


    public long count() {
        return alertRepo.count();
    }


    public Alert create(Alert alert) {
        alert.setId(null);
        return alertRepo.save(alert);
    }

    public Alert update(Alert alert) {
        assertExistById(alert.getId());
        return alertRepo.save(alert);
    }

    public void markAsRead(int alertId) {
        Alert alert = findById(alertId);
        alert.setStatus((short) 1);
        alertRepo.save(alert);
    }


    public void deleteById(int id) {
        assertExistById(id);
        alertRepo.deleteById(id);
    }

    public void delete(Alert alert) {
        alertRepo.delete(alert);
    }

    public Alert findById(int id) {
        return alertRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alert '" + id + "' Not Found"));
    }

    public void assertExistById(int id) {
        if (!alertRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alert '" + id + "' Not Found");
    }


    public void alertUpdateInternship(Internship internship, int oldSts, int newSts) {
        if (oldSts == newSts)
            return;
        InternshipStatus oldStatus = InternshipStatus.valueOf(oldSts);
        InternshipStatus newStatus = InternshipStatus.valueOf(newSts);
        assert oldStatus != null && newStatus != null;

        Alert alert = new Alert();
        alert.setContent("La Status du Stage '" + internship.getId()
                + "' est changé de '" + oldStatus.getFrValue()
                + "' à '" + newStatus.getFrValue() + "'.");
        alert.setInternship(internship);

        alertRepo.save(alert);
    }
}
