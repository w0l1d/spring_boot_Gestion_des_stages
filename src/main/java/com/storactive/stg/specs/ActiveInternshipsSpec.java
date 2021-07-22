package com.storactive.stg.specs;

import com.storactive.stg.model.Interner;
import com.storactive.stg.model.Internship;
import com.storactive.stg.model.Internship_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import java.sql.Date;

public class ActiveInternshipsSpec {

    public static Specification<Internship> getCurrInternshipsSpec() {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Expression<Date> now = criteriaBuilder.currentDate();
            return criteriaBuilder.between(now, root.get(Internship_.STARTS_AT), root.get(Internship_.ENDS_AT));
        };
    }


    public static Specification<Internship> getOwnedCurrInternshipsSpec(Interner interner) {
        return getCurrInternshipsSpec()
                .and(InternerOwnSpec.getInternshipSpec(interner));
    }
}
