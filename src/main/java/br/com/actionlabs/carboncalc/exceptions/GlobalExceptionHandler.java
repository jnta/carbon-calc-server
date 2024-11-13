package br.com.actionlabs.carboncalc.exceptions;

import br.com.actionlabs.carboncalc.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .details(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalUfException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalUfException(IllegalUfException ex, WebRequest request) {
        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .details(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .message(errorMessage)
                .timestamp(LocalDateTime.now())
                .details(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception ex, WebRequest request) {
        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .details(request.getDescription(false))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
