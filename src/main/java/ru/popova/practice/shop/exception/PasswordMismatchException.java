package ru.popova.practice.shop.exception;

import lombok.Getter;

@Getter
public class PasswordMismatchException extends RuntimeException {

    private String objectName;

    public PasswordMismatchException(String objectName, String message) {
        this(message);
        this.objectName = objectName;
    }
    public PasswordMismatchException() {
    }

    public PasswordMismatchException(String message) {
        super(message);
    }

    public PasswordMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordMismatchException(Throwable cause) {
        super(cause);
    }

    public PasswordMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
