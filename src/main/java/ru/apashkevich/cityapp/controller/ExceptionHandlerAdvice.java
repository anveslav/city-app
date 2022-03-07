package ru.apashkevich.cityapp.controller;

import com.sun.security.auth.UnixNumericUserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.apashkevich.cityapp.dto.ErrorResponse;
import ru.apashkevich.cityapp.exception.CityBaseException;
import ru.apashkevich.cityapp.exception.CityDatabaseException;
import ru.apashkevich.cityapp.exception.EntityNotFoundException;
import ru.apashkevich.cityapp.exception.ValidationException;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    private static final String DEFAULT_ERROR_MESSAGE = "Unpredictable error occurred";
    private static final String SERVICE_ERROR_MESSAGE = "Service error";
    private static final String DATABASE_ERROR_MESSAGE = "Database error";
    private static final String FORBIDDEN_ERROR_MESSAGE = "Access denied for this action";
    private static final String INCORRECT_INPUT = "Incorrect input";
    private static final String ENTITY_NOT_FOUND = "Entity not found";

    @ExceptionHandler(CityBaseException.class)
    public ResponseEntity<ErrorResponse> cityBaseException(CityBaseException e) {
        log.error(SERVICE_ERROR_MESSAGE, e);
        return status(INTERNAL_SERVER_ERROR).body(buildError(SERVICE_ERROR_MESSAGE));
    }

    @ExceptionHandler(CityDatabaseException.class)
    public ResponseEntity<ErrorResponse> DBException(CityDatabaseException e) {
        log.error(DATABASE_ERROR_MESSAGE, e);
        return status(INTERNAL_SERVER_ERROR).body(buildError(DATABASE_ERROR_MESSAGE));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundException(EntityNotFoundException e) {
        log.error(ENTITY_NOT_FOUND, e);
        return status(NOT_FOUND).body(buildError(e.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> validationException(ValidationException e) {
        log.error(INCORRECT_INPUT, e);
        return status(BAD_REQUEST).body(buildError(e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> accessDeniedException(AccessDeniedException e) {
        log.error(FORBIDDEN_ERROR_MESSAGE, e);
        return status(FORBIDDEN).body(buildError(FORBIDDEN_ERROR_MESSAGE));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> throwable(Throwable e) {
        log.error(DEFAULT_ERROR_MESSAGE, e);
        return status(INTERNAL_SERVER_ERROR).body(buildError(DEFAULT_ERROR_MESSAGE));
    }

    private ErrorResponse buildError(String message) {
        return ErrorResponse.builder()
                .message(message)
                .build();
    }
}
