package ru.popova.practice.shop.exception;

import lombok.Getter;

@Getter
public class NotAllowedException extends RuntimeException {

    private String objectName;

    public NotAllowedException(String objectName, String message) {
        this(message);
        this.objectName = objectName;
    }

    public NotAllowedException() {
    }

    public NotAllowedException(String message) {
        super(message);
    }

    public NotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAllowedException(Throwable cause) {
        super(cause);
    }

    public NotAllowedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
