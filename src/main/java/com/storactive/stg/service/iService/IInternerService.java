package com.storactive.stg.service.iService;

import com.storactive.stg.model.Interner;

public interface IInternerService {

    Interner create(Interner interner);

    Interner update(Interner interner);

    void delete(Integer id);

    Interner findById(int id);
}
