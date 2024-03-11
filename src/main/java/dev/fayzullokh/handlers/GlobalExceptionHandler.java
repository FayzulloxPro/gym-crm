package dev.fayzullokh.handlers;

import dev.fayzullokh.dtos.AppErrorDTO;
import dev.fayzullokh.exceptions.DuplicateUsernameException;
import dev.fayzullokh.exceptions.NotFoundException;
import dev.fayzullokh.exceptions.UnknownException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppErrorDTO> handleUnknownExceptions(Exception e, HttpServletRequest request) {
        log.error("Unknown exception occurred", e);
        return ResponseEntity.badRequest().body(
                new AppErrorDTO(
                        request.getRequestURI(),
                        e.getMessage(),
                        null,
                        400
                )
        );
    }

    @ExceptionHandler(UnknownException.class)
    public ResponseEntity<AppErrorDTO> handleUnknownException(UnknownException e, HttpServletRequest request) {
        log.error("Unknown exception occurred", e);
        return ResponseEntity.badRequest().body(
                new AppErrorDTO(
                        request.getRequestURI(),
                        e.getMessage(),
                        null,
                        400
                )
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<AppErrorDTO> handleRuntimeExceptions(RuntimeException e, HttpServletRequest request) {
        log.error("Runtime exception occurred", e);
        return ResponseEntity.badRequest().body(
                new AppErrorDTO(
                        request.getRequestURI(),
                        e.getMessage(),
                        null,
                        400
                )
        );
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<AppErrorDTO> handleNotFoundExceptions(NotFoundException e, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(
                new AppErrorDTO(
                        request.getRequestURI(),
                        e.getMessage(),
                        null,
                        404
                )
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<AppErrorDTO> handleUsernameNotFoundExceptions(UsernameNotFoundException e, HttpServletRequest request) {
        log.error("Username not found exception occurred", e);
        return ResponseEntity.badRequest().body(
                new AppErrorDTO(
                        request.getRequestURI(),
                        e.getMessage(),
                        null,
                        404
                )
        );
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<AppErrorDTO> handleInsufficientAuthenticationException(InsufficientAuthenticationException e, HttpServletRequest request) {
        log.error("Insufficient authentication exception occurred", e);
        return ResponseEntity.badRequest().body(
                new AppErrorDTO(
                        request.getRequestURI(),
                        e.getMessage(),
                        null,
                        400
                )
        );
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<AppErrorDTO> handleDuplicateUsernameExceptions(DuplicateUsernameException e, HttpServletRequest request) {
        log.error("Duplicate username exception occurred", e);
        return ResponseEntity.badRequest().body(
                new AppErrorDTO(
                        request.getRequestURI(),
                        e.getMessage(),
                        null,
                        409
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("Method argument not valid exception occurred", e);

        String errorMessage = "Input is not valid";
        Map<String, List<String>> errorBody = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errorBody.compute(field, (s, values) -> {
                if (!Objects.isNull(values))
                    values.add(message);
                else
                    values = new ArrayList<>(Collections.singleton(message));
                return values;
            });
        }
        String errorPath = request.getRequestURI();
        AppErrorDTO errorDTO = new AppErrorDTO(errorPath, errorMessage, errorBody, 400);
        return ResponseEntity.status(400).body(errorDTO);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<AppErrorDTO> handleExpiredJwtException(ExpiredJwtException e, HttpServletRequest request) {
        // You can customize the response message as needed
        log.error("JWT token expired", e);
        return ResponseEntity.badRequest().body(
                new AppErrorDTO(
                        request.getRequestURI(),
                        e.getMessage(),
                        null,
                        401
                )
        );
    }
}
