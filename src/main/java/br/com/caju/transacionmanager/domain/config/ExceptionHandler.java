package br.com.caju.transacionmanager.domain.config;

import br.com.caju.transacionmanager.domain.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        ErrorResponseDTO response = new ErrorResponseDTO();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setErrors(Collections.singletonList(Map.of("error", ex.getMessage())));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorResponseDTO response = new ErrorResponseDTO();
        response.setStatus(ex.getStatusCode().value());
        response.setErrors(new ArrayList<>());
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            assert errorMessage != null;
            response.getErrors().add(Map.of(fieldName, errorMessage));
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
