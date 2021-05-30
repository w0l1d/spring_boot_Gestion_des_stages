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

    final StageRepo stageRepo;

    @Autowired
    public StageService(StageRepo stageRepo) {
        this.stageRepo = stageRepo;
    }


    public List<Internship> getAll() {
        return stageRepo.findAll();
    }

    public Internship create(Internship internship) {
        internship.setId(null);
        return stageRepo.save(internship);
    }

    public Internship update(Internship internship) {
        if (!stageRepo.existsById(internship.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return stageRepo.save(internship);
    }

    public void delete(Integer id) {
        if (stageRepo.existsById(id))
            stageRepo.deleteById(id);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public Internship findById(int id) {
        return stageRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


}
