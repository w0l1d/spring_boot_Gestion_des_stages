package com.storactive.stg.service.iService;

import com.storactive.stg.model.Stagiaire;
import java.util.List;

public interface IStagiaireService {
    List<Stagiaire> getAll();

    Stagiaire create(Stagiaire stagiaire);

    Stagiaire update(Stagiaire stagiaire);

    void delete(Integer id);

    Stagiaire findById(int id);
}
