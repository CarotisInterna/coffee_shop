package ru.popova.practice.shop.exception;

import lombok.Getter;

@Getter
public class AlreadyExistsException extends RuntimeException {

    private String objectName;

    public AlreadyExistsException(String objectName, String message) {
        this(message);
        this.objectName = objectName;
    }
    public AlreadyExistsException() {
    }

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public AlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
