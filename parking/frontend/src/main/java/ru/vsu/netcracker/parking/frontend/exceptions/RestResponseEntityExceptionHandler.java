package ru.vsu.netcracker.parking.frontend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestResponseEntityExceptionHandler{

    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Resourse not found")
    @ExceptionHandler(ResourceNotFoundException.class)
    public void handleNotFound(RuntimeException ex, WebRequest request) {
        // ToDo Logging
    }
}
