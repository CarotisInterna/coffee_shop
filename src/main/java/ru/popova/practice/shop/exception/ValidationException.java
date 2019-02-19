package ru.popova.practice.shop.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;
import ru.popova.practice.shop.dto.ListErrorDto;

@Getter
public class ValidationException extends RuntimeException {

    private BindingResult bindingResult;

    private ListErrorDto listErrorDto = new ListErrorDto();

    public ValidationException(BindingResult bindingResult, ListErrorDto listErrorDto) {
        this.bindingResult = bindingResult;
        this.listErrorDto = listErrorDto;
    }

    public ValidationException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
}
