package ru.autoins.oto_registry_rest.rest.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.autoins.oto_registry_rest.rest.error.RegistryErrorResponse;
import ru.autoins.oto_registry_rest.rest.exeptions.WrongDateException;

import java.time.LocalDateTime;

@ControllerAdvice
public class RegistryRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<RegistryErrorResponse> handleException (WrongDateException e) {
        RegistryErrorResponse errorResponse = new RegistryErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<RegistryErrorResponse> handleException (Exception e) {
        RegistryErrorResponse errorResponse = new RegistryErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
