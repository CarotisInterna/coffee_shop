package ru.popova.practice.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.popova.practice.shop.dto.ErrorDto;
import ru.popova.practice.shop.dto.ListErrorDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * обработчик ошибок
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(errorDto(e.getObjectName(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ListErrorDto> handleValidationException(ValidationException e) {
        return new ResponseEntity<>(validationErrors(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleAlreadyExistException(AlreadyExistsException e) {
        return new ResponseEntity<>(errorDto(e.getObjectName(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorDto> handlePasswordMismatchException(PasswordMismatchException e) {
        return new ResponseEntity<>(errorDto(e.getObjectName(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private ErrorDto errorDto(String objectName, String message) {
        return new ErrorDto(objectName ,message);
    }

    private ListErrorDto validationErrors(ValidationException ex) {
        List<ErrorDto> collectedErrors = ex.getBindingResult().getAllErrors()
                .stream()
                .filter(error -> error instanceof FieldError)
                .map(err -> {
                    FieldError error = (FieldError) err;
                    return new ErrorDto(error.getField(), error.getDefaultMessage());
                })
                .collect(Collectors.toList());
        return new ListErrorDto(collectedErrors);

    }
}
