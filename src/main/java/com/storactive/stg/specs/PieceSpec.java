package com.storactive.stg.specs;

import com.storactive.stg.model.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PieceSpec {

    public static Specification<Piece> piecesNotRelatedToInternshipSpec(Internship internship) {
        return (root, cq, cb) -> {
            Root<StagePiece> sPieceRoot = cq.from(StagePiece.class);

            Predicate predicate = cb.disjunction();

            predicate.getExpressions().add(
                    cb.equal(sPieceRoot.get(StagePiece_.internship)
                            .get(Internship_.ID), internship.getId())
            );

            predicate.getExpressions().add(
                    cb.notEqual(root.get(Piece_.ID), sPieceRoot.get(StagePiece_.piece).get(Piece_.ID))
            );

            cq.distinct(true);
            return predicate;

        };
    }


}
