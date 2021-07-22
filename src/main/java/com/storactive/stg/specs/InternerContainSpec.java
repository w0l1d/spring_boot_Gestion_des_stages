package com.storactive.stg.specs;

import com.storactive.stg.model.Interner;
import com.storactive.stg.model.Interner_;
import org.springframework.data.jpa.domain.Specification;

import static org.springframework.data.jpa.domain.Specification.where;

public class InternerContainSpec {
    private static Specification<Interner> intrIdSpec(String s) {
        return (root, cq, cb) -> cb.like(root.get(Interner_.ID).as(String.class), "%" + s + "%");
    }

    private static Specification<Interner> intrNameSpec(String s) {
        return (root, cq, cb) -> cb.like(root.get(Interner_.NAME), "%" + s + "%");
    }

    private static Specification<Interner> intrEmailSpec(String s) {
        return (root, cq, cb) -> cb.like(root.get(Interner_.email), "%" + s + "%");
    }

    private static Specification<Interner> intrCinSpec(String s) {
        return (root, cq, cb) -> cb.like(root.get(Interner_.CIN), "%" + s + "%");
    }

    private static Specification<Interner> intrPhoneSpec(String s) {
        return (root, cq, cb) -> cb.like(root.get(Interner_.PHONE), "%" + s + "%");
    }

    private static Specification<Interner> intrUsernameSpec(String s) {
        return (root, cq, cb) -> cb.like(root.get(Interner_.USERNAME), "%" + s + "%");
    }

    public static Specification<Interner> getInternerSpec(String s) {
        return where(intrIdSpec(s))
                .or(intrCinSpec(s))
                .or(intrNameSpec(s))
                .or(intrEmailSpec(s))
                .or(intrPhoneSpec(s))
                .or(intrUsernameSpec(s));
    }

}
