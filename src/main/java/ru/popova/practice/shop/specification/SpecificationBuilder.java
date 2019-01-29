package ru.popova.practice.shop.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder<T> {

    private List<Specification<T>> specifications;

    public SpecificationBuilder() {
        specifications = new ArrayList<>();
    }

    public SpecificationBuilder with(Specification<T> specification) {
        specifications.add(specification);
        return this;
    }

    public Specification<T> build() {
        if (specifications.isEmpty()) {
            return null;
        }

        Specification<T> result = specifications.get(0);

        for (int i = 1; i < specifications.size(); i++) {
            result = Specification.where(result).and(specifications.get(i));
        }
        return result;
    }

    public <X extends Comparable<X>> SpecificationBuilder<T> between(SingularAttribute<T, X> name, X lower, X upper) {
        if (lower != null && upper != null) {
            this.with(((root, query, builder) ->
                    builder.between(root.get(name), lower, upper)
            ));
        } else if (lower != null) {
            this.with(((root, query, builder) ->
                    builder.greaterThanOrEqualTo(root.get(name), lower)
            ));
        } else if (upper != null) {
            this.with(((root, query, builder) ->
                    builder.lessThanOrEqualTo(root.get(name), upper)
            ));
        }
        return this;
    }
}
