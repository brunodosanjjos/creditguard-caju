package br.com.caju.transacionmanager.domain.config;

import br.com.caju.transacionmanager.domain.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        ErrorResponseDTO response = createErrorResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> Map.of(((FieldError) error).getField(), Objects.requireNonNull(error.getDefaultMessage())))
                .collect(Collectors.toList());

        ErrorResponseDTO response = createErrorResponses(errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponseDTO createErrorResponse(String errorMessage) {
        ErrorResponseDTO response = new ErrorResponseDTO();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setErrors(Collections.singletonList(Map.of("error", errorMessage)));
        return response;
    }

    private ErrorResponseDTO createErrorResponses(List<Map<String, String>> errors) {
        ErrorResponseDTO response = new ErrorResponseDTO();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setErrors(errors);
        return response;
    }

}
