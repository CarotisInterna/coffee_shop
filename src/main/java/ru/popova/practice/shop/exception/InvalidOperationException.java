package ru.popova.practice.shop.exception;

import lombok.Getter;

@Getter
public class InvalidOperationException extends RuntimeException {

    private String objectName;

    public InvalidOperationException(String objectName, String message) {
        this(message);
        this.objectName = objectName;
    }

    public InvalidOperationException() {
    }

    public InvalidOperationException(String message) {
        super(message);
    }

    public InvalidOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOperationException(Throwable cause) {
        super(cause);
    }

    public InvalidOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
