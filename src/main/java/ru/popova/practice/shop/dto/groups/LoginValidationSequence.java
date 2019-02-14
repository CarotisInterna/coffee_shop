package ru.popova.practice.shop.dto.groups;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * интерфейс с последовательностью групп валидации пользователя, который хочет залогиниться
 */
@GroupSequence({NotEmptyGroup.class, LoginGroup.class, Default.class})
public interface LoginValidationSequence {
}
