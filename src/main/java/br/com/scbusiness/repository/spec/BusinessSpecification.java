package br.com.scbusiness.repository.spec;

import br.com.scbusiness.enums.BusinessStatus;
import br.com.scbusiness.enums.Segment;
import br.com.scbusiness.model.Business;
import org.springframework.data.jpa.domain.Specification;

public class BusinessSpecification {

    private BusinessSpecification() {}

    public static Specification<Business> hasStatus(BusinessStatus status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Business> hasSegment(Segment segment) {
        return (root, query, cb) ->
                segment == null ? null : cb.equal(root.get("segment"), segment);
    }
}
