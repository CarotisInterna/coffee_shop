package ru.popova.practice.shop.dto.groups;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * интерфейс с последовательностью групп валидации
 */
@GroupSequence({NotEmptyGroup.class, Default.class})
public interface NotEmptyValidationSequence {
}
