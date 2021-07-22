package com.storactive.stg.specs;

import com.storactive.stg.model.Internship;
import com.storactive.stg.model.Internship_;
import org.springframework.data.jpa.domain.Specification;

import static org.springframework.data.jpa.domain.Specification.where;

public class InternshipContainSpec {
    private static Specification<Internship> intrIdSpec(String s) {
        return (root, cq, cb) -> cb.like(root.get(Internship_.ID).as(String.class), "%" + s + "%");
    }

    private static Specification<Internship> intrProjectSpec(String s) {
        return (root, cq, cb) -> cb.like(root.get(Internship_.PROJECT), "%" + s + "%");
    }

    private static Specification<Internship> intrTypeSpec(String s) {
        return (root, cq, cb) -> cb.like(root.get(Internship_.TYPE), "%" + s + "%");
    }

    private static Specification<Internship> intrFormationSpec(String s) {
        return (root, cq, cb) -> cb.like(root.get(Internship_.FORMATION), "%" + s + "%");
    }

    private static Specification<Internship> intrEtablissementSpec(String s) {
        return (root, cq, cb) -> cb.like(root.get(Internship_.ETABLISSEMENT), "%" + s + "%");
    }

    private static Specification<Internship> intrDescSpec(String s) {
        return (root, cq, cb) -> cb.like(root.get(Internship_.DESC), "%" + s + "%");
    }

    private static Specification<Internship> intrDurationSpec(String s) {
        return (root, cq, cb) -> cb.like(root.get(Internship_.DURATION), "%" + s + "%");
    }

    public static Specification<Internship> getInternshipSpec(String s) {
        return where(intrIdSpec(s))
                .or(intrProjectSpec(s))
                .or(intrDurationSpec(s))
                .or(intrDescSpec(s))
                .or(intrEtablissementSpec(s))
                .or(intrFormationSpec(s))
                .or(intrTypeSpec(s));
    }

}
