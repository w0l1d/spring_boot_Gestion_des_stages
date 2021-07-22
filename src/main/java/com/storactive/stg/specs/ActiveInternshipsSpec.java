package com.storactive.stg.specs;

import com.storactive.stg.model.Interner;
import com.storactive.stg.model.Interner_;
import com.storactive.stg.model.Internship;
import com.storactive.stg.model.Internship_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;

public class ActiveInternshipsSpec {

    public static Specification<Internship> getCurrInternshipsSpec() {
        LocalDate now = LocalDate.now();
        return (root, criteriaQuery, criteriaBuilder) -> {

            return criteriaBuilder.and(
                    criteriaBuilder.lessThanOrEqualTo(root.get(Internship_.STARTS_AT), now),
                    criteriaBuilder.greaterThanOrEqualTo(root.get(Internship_.ENDS_AT), now)
            );
        };
    }

    private static Specification<Internship> getLessThanSpec(LocalDate date) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get(Internship_.STARTS_AT), date);
        });
    }

    private static Specification<Internship> getGreaterThanSpec(LocalDate date) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(Internship_.ENDS_AT), date);
        });
    }

    public static Specification<Internship> getOwnedCurrInternshipsSpec(Interner interner) {
        return (root, criteriaQuery, criteriaBuilder) -> {

            Predicate predicate = criteriaBuilder.disjunction();
            LocalDate now = LocalDate.now();
            predicate.getExpressions().add(
                    criteriaBuilder.and(
                            criteriaBuilder.lessThanOrEqualTo(root.get(Internship_.STARTS_AT), now),
                            criteriaBuilder.greaterThanOrEqualTo(root.get(Internship_.ENDS_AT), now),
                            criteriaBuilder.equal(root.get(Internship_.INTERNER).get(Interner_.ID), interner.getId())
                    )
            );
            return predicate;
        };
    }
}
