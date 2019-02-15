package ru.popova.practice.shop.exception;

import lombok.Getter;
import ru.popova.practice.shop.dto.ErrorDto;
import ru.popova.practice.shop.dto.ListErrorDto;

import java.util.ArrayList;

@Getter
public class NotFoundException extends RuntimeException {

    private String objectName;

    private ListErrorDto listErrorDto;

    public NotFoundException(ListErrorDto listErrorDto) {
        this.listErrorDto = listErrorDto;
    }

    public NotFoundException(String objectName, String message) {
        this(message);
        this.objectName = objectName;
    }

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    protected NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
