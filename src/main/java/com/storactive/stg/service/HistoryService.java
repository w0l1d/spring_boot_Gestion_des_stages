package com.storactive.stg.service;

import com.storactive.stg.model.History;
import com.storactive.stg.repository.HistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class HistoryService {

    final HistoryRepo historyRepo;

    @Autowired
    public HistoryService(HistoryRepo historyRepo) {
        this.historyRepo = historyRepo;
    }


    public List<History> getAll() {
        return historyRepo.findAll();
    }

    public void create(String action) {
        History history = new History();
        history.setAction(action);
        historyRepo.save(history);
    }

    public void objetCreated(String obj, int objId) {
        create(obj + " '" + objId + "' est créé");
    }

    public void objetDeleted(String obj, int objId) {
        create(obj + " '" + objId + "' est supprimé");
    }

    public void objetUpdated(String obj, int objId) {
        create(obj + " '" + objId + "' est modifié");
    }


    public void deleteAll() {
        historyRepo.deleteAll();
    }

    public void deleteAllSpecified(Iterable<History> actions) {
        historyRepo.deleteAll(actions);
    }


    public History findById(int id) {
        return historyRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "History Not Found"));
    }


    public boolean existsById(int id) {
        return historyRepo.existsById(id);
    }

}
