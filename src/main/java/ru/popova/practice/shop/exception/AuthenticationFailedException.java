package ru.popova.practice.shop.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class AuthenticationFailedException extends AuthenticationException {

    private String objectName;

    public AuthenticationFailedException(String objectName, String msg) {
        this(msg);
        this.objectName = objectName;
    }

    public AuthenticationFailedException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthenticationFailedException(String msg) {
        super(msg);
    }
}
