package br.com.erline.portfolio.interfaces.rest.handler;

import br.com.erline.portfolio.domain.exception.DepoimentoNotFoundException;
import br.com.erline.portfolio.domain.exception.DomainException;
import br.com.erline.portfolio.domain.exception.UsuarioAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(
            DomainException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de domínio",
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception
    ) {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                "Existem dados inválidos na requisição.",
                LocalDateTime.now(),
                errors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    public record ErrorResponse(
            int status,
            String error,
            String message,
            LocalDateTime timestamp,
            List<String> details
    ) {
    }

    @ExceptionHandler(DepoimentoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDepoimentoNotFound(
            DepoimentoNotFoundException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(UsuarioAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsuarioAlreadyExists(
            UsuarioAlreadyExistsException exception
    ) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Conflito",
                exception.getMessage(),
                LocalDateTime.now(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }
}