package com.storactive.stg.service;

import com.storactive.stg.Utils;
import com.storactive.stg.model.*;
import com.storactive.stg.model.enums.InternshipStatus;
import com.storactive.stg.repository.AlertRepo;
import com.storactive.stg.specs.InternerOwnSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlertService {

    @Value("${spring.custom.notif.max-alerts}")
    private int MAX_ALERT;

    final AlertRepo alertRepo;

    public Page<Alert> getIndex() {
        return alertRepo.findAll(PageRequest.ofSize(MAX_ALERT).withSort(Sort.Direction.DESC, Alert_.CREATED_AT));
    }

    public Page<Alert> getIndexInterner() {
        return alertRepo.findAll(
                InternerOwnSpec.getAlertSpec((Interner) Utils.getCurrUser()),
                PageRequest.ofSize(MAX_ALERT).withSort(Sort.Direction.DESC, Alert_.CREATED_AT)
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
        alert.setContent("Status du Stage '" + internship.getId()
                + "' est changé de '" + oldStatus.getFrValue()
                + "' à '" + newStatus.getFrValue() + "'.");
        alert.setInternship(internship);

        alertRepo.save(alert);
    }


    public void alertInternshipPieces(Internship internship, List<Piece> pieces) {
        if (pieces.isEmpty())
            return;

        int numPieces = pieces.size();

        Alert alert = new Alert();
        alert.setInternship(internship);

        StringBuilder content = new StringBuilder();
        content.append("Stage '").append(internship.getId()).append("' a besoin");

        StringBuilder pieceString = new StringBuilder();
        if (numPieces < 4) {
            int i = 0;
            for (Piece p : pieces) {
//                if (i == 0)
//                    pieceString.append(" ");
                pieceString.append(p.getLabel());
                if (++i == numPieces)
                    pieceString.append(".");
                else
                    pieceString.append(", ");

            }
            content.append(" des pieces suivantes :").append(pieceString);
        } else
            content.append(" de ").append(numPieces).append(" Pieces");


        alert.setContent(content.toString());

        alertRepo.save(alert);
    }


}
