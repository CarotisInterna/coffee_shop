package ru.popova.practice.shop.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.List;

/**
 * сборщик спецификаций для поиска напитков по заданным параметрам
 *
 * @param <T> для какого типа объектов создается спецификация
 */
public class SpecificationBuilder<T> {

    /**
     * список спецификаций
     */
    private List<Specification<T>> specifications;

    public SpecificationBuilder() {
        specifications = new ArrayList<>();
    }

    /**
     * добавление спецификации
     *
     * @param specification спецификация
     * @return
     */
    public SpecificationBuilder with(Specification<T> specification) {
        specifications.add(specification);
        return this;
    }

    /**
     * сборка спецификации из нескольких спецификаций
     *
     * @return спецификация
     */
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

    /**
     * создание объекта для фильтрации с помощью операций сравнения
     *
     * @param name  название поля, для которого применяется фильтрация
     * @param lower нижняя граница
     * @param upper верхняя граница
     * @param <X>   тип границ
     * @return объект с необходимой спецификацией
     */
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
