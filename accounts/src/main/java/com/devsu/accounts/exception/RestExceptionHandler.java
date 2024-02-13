package com.devsu.accounts.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        logger.error("Error encountered: {}", ex.getMessage(), ex);

        GlobalErrorResponse globalErrorResponse = new GlobalErrorResponse();

        List<GeneralError> generalErrors = new ArrayList<>();
        GeneralError generalError = new GeneralError(ex.getStatusCode().value(), ex.getReason());
        generalErrors.add(generalError);
        globalErrorResponse.setErrors(generalErrors);
        return new ResponseEntity<>(globalErrorResponse, ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error("Error inesperado: {}", ex.getMessage(), ex);

        List<GeneralError> generalErrors = new ArrayList<>();
        GeneralError generalError = new GeneralError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrió un error inesperado. Por favor, intente nuevamente más tarde.");
        generalErrors.add(generalError);
        GlobalErrorResponse globalErrorResponse = new GlobalErrorResponse(generalErrors);
        return new ResponseEntity<>(globalErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.info("Manejando error de validación para: {}", request.getDescription(false));

        List<GeneralError> errors = ex.getBindingResult().getAllErrors().stream().map(this::mapToGeneralError).collect(Collectors.toList());

        GlobalErrorResponse globalErrorResponse = new GlobalErrorResponse(errors);

        return new ResponseEntity<>(globalErrorResponse, HttpStatus.BAD_REQUEST);
    }


    private GeneralError mapToGeneralError(ObjectError error) {
        if (error != null) {
            return new GeneralError(HttpStatus.BAD_REQUEST.value(), String.format("%s: %s", ((FieldError) error).getField(), error.getDefaultMessage()));
        } else {
            return new GeneralError(HttpStatus.BAD_REQUEST.value(), error.getDefaultMessage());
        }
    }

}
