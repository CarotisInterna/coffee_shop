package ru.popova.practice.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.popova.practice.shop.dto.ErrorDto;
import ru.popova.practice.shop.dto.ListErrorDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Обработчик ошибок
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        if (e.getListErrorDto().getErrorDtos().isEmpty()) {
            return new ResponseEntity<>(errorDto(e.getObjectName(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(e.getListErrorDto(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ListErrorDto> handleValidationException(ValidationException e) {
        return new ResponseEntity<>(collectValidationErrors(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleAlreadyExistException(AlreadyExistsException e) {
        return new ResponseEntity<>(errorDto(e.getObjectName(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new ResponseEntity<>(errorDto("username", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handlePasswordMismatchException(PasswordMismatchException e) {
        return new ResponseEntity<>(errorDto(e.getObjectName(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotAllowedException.class)
    public ResponseEntity<ErrorDto> handleNotAllowedException(NotAllowedException e) {
        return new ResponseEntity<>(errorDto(e.getObjectName(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorDto> handleAuthenticationFailedException(AuthenticationFailedException e) {
        return new ResponseEntity<>(errorDto(e.getObjectName(), e.getMessage()), HttpStatus.UNAUTHORIZED);
    }


    private ErrorDto errorDto(String objectName, String message) {
        return new ErrorDto(objectName, message);
    }

    /**
     * Формирование списка дто ошибок
     *
     * @param ex валидационное исключение
     * @return список дто ошибок
     */
    private ListErrorDto collectValidationErrors(ValidationException ex) {
        List<ErrorDto> collectedErrors = ex.getBindingResult().getAllErrors()
                .stream()
                .filter(error -> error instanceof FieldError)
                .map(err -> {
                    FieldError error = (FieldError) err;
                    return new ErrorDto(error.getField(), error.getDefaultMessage());
                })
                .collect(Collectors.toList());
        collectedErrors.addAll(ex.getListErrorDto().getErrorDtos());

        return new ListErrorDto(collectedErrors);

    }

}
