package com.example.companydemo.core;

import com.example.companydemo.company.CompanyException;
import com.example.companydemo.company.CompanyNotFoundException;
import com.example.companydemo.company.CompanyUniqueViolationException;
import lombok.Data;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Rest exception handler.
 *
 * @author Sasa Bolic
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleBadRequest(Exception e) {
        return buildResponseEntity(RestError.of(HttpStatus.BAD_REQUEST, e));
    }

    @ExceptionHandler({CompanyNotFoundException.class})
    public ResponseEntity<Object> handleNotFound(Exception e) {
        return buildResponseEntity(RestError.of(HttpStatus.NOT_FOUND, e));
    }

    @ExceptionHandler({CompanyUniqueViolationException.class, DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleAlreadyExist(Exception e) {
        return buildResponseEntity(RestError.of(HttpStatus.CONFLICT, e));
    }

    @ExceptionHandler({CompanyException.class})
    protected ResponseEntity<Object> handleCompanyException(CompanyException ex) {
        RestError restError = RestError.of(HttpStatus.BAD_REQUEST, ex, ex.getMessage());
        restError.addErrors(ex.getExceptions());

        return buildResponseEntity(restError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();

        RestError restError = RestError.of(status, ex, "Validation failed");
        restError.addValidationErrors(bindingResult.getAllErrors());

        return buildResponseEntity(restError);
    }

    private ResponseEntity<Object> buildResponseEntity(RestError restError) {
        return ResponseEntity.status(restError.getStatus()).body(restError);
    }

    /**
     * REST error object.
     */
    @Data
    static class RestError {

        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String exception;
        private String message;
        private List<String> errors;

        private RestError(LocalDateTime timestamp,
                          HttpStatus status,
                          String error,
                          String exception,
                          String message) {
            this.timestamp = timestamp;
            this.status = status.value();
            this.error = error;
            this.exception = exception;
            this.message = message;
        }

        static RestError of(HttpStatus status, Throwable ex) {
            return of(status, ex, ex.getLocalizedMessage());
        }

        static RestError of(HttpStatus status, Throwable ex, String message) {
            return new RestError(
                    LocalDateTime.now(),
                    status,
                    status.getReasonPhrase(),
                    ex.getClass().getName(),
                    message);
        }

        void addValidationErrors(List<? extends ObjectError> bindErrors) {
            this.errors = bindErrors.stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        }

        void addErrors(List<? extends Exception> errors) {
            this.errors = errors.stream()
                    .map(Exception::getMessage).collect(Collectors.toList());
        }
    }
}
