package com.mastery.java.task.exeption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmployeeServiceNotFoundException.class)
    public Map<String, String> handleEmployeeNotFoundException(EmployeeServiceNotFoundException e) {
        log.error("Error: ", e);
        Map<String, String> errors = new HashMap<>();
        errors.put("message", e.getMessage());
        return errors;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({DataAccessException.class, IllegalArgumentException.class})
    public Map<String, String> handleDataException(RuntimeException e) {
        log.error("Error: ", e);
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Exception of data access");
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Map<String, String> handleArgumentTypeException(MethodArgumentTypeMismatchException ex) {
        log.error("Error: ", ex);
        Map<String, String> error = new HashMap<>();
        ;
        if (ex.getRequiredType() != null) {
            error.put("message", ex.getName() + " in request URL should be of type " + ex.getRequiredType().getName());
        } else {
            error.put("message", ex.getName() + " in request URL must not be empty");
        }
        return error;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException e) {
        log.error("Error: ", e);
        return e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, fieldError -> Optional.of(fieldError)
                        .map(FieldError::getDefaultMessage)
                        .orElse("Error field value")));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleConstraintValidationExceptions(ConstraintViolationException e) {
        log.error("Error: ", e);
        return e.getConstraintViolations().stream()
                .collect(Collectors.toMap(p -> p.getPropertyPath().toString(), ConstraintViolation::getMessage));
    }
}
