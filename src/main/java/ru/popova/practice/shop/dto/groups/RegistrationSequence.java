package ru.popova.practice.shop.dto.groups;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * интерфейс с последовательностью групп валидации при регистрации
 */
@GroupSequence({NotEmptyGroup.class, RegistrationGroup.class, Default.class})
public interface RegistrationSequence {
}
