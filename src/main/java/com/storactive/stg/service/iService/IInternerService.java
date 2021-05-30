package com.storactive.stg.service.iService;

import com.storactive.stg.model.Interner;

import java.util.List;

public interface IInternerService {
    List<Interner> getAll();

    Interner create(Interner interner);

    Interner update(Interner interner);

    void delete(Integer id);

    Interner findById(int id);
}
