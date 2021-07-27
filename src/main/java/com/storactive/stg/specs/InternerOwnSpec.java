package com.storactive.stg.specs;

import com.storactive.stg.model.*;
import org.springframework.data.jpa.domain.Specification;

public class InternerOwnSpec {
    public static Specification<Absence> getAbsenceSpec(Interner interner) {
        return (root, cq, cb) -> cb.equal(root.get(Absence_.INTERNSHIP).get(Internship_.INTERNER).get(Interner_.ID), interner.getId());
    }

    public static Specification<Task> getTaskSpec(Interner interner) {
        return (root, cq, cb) -> cb.equal(root.get(Task_.INTERNSHIP).get(Internship_.INTERNER).get(Interner_.ID), interner.getId());
    }

    public static Specification<StagePiece> getDocSpec(Interner interner) {
        return (root, cq, cb) -> cb.equal(root.get(StagePiece_.INTERNSHIP).get(Internship_.INTERNER).get(Interner_.ID), interner.getId());
    }

    public static Specification<Internship> getInternshipSpec(Interner interner) {
        return (root, cq, cb) -> cb.equal(root.get(Internship_.INTERNER).get(Interner_.ID), interner.getId());
    }

    public static Specification<History> getHistorySpec(User user) {
        return (root, cq, cb) -> cb.equal(root.get(History_.CREATED_BY).get(User_.ID), user.getId());
    }

    public static Specification<Alert> getAlertSpec(Interner interner) {
        return (root, cq, cb) -> cb.equal(root.get(Alert_.INTERNSHIP)
                .get(Internship_.INTERNER).get(Interner_.ID), interner.getId());
    }


}
