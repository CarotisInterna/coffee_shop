package ru.popova.practice.shop.exception;

import lombok.Getter;
import org.springframework.security.authentication.BadCredentialsException;

@Getter
public class PasswordMismatchException extends BadCredentialsException {

    private String objectName;

    public PasswordMismatchException(String objectName, String message) {
        this(message);
        this.objectName = objectName;
    }

    public PasswordMismatchException(String message) {
        super(message);
    }

    public PasswordMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
