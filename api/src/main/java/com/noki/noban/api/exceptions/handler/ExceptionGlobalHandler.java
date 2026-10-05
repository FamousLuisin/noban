package com.noki.noban.api.exceptions.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import com.noki.noban.api.exceptions.ExceptionResponse;
import com.noki.noban.api.exceptions.PermissionDeniedException;
import com.noki.noban.api.exceptions.ResourceNotFoundException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

@ControllerAdvice
public class ExceptionGlobalHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponse> handlerResponseStatusException(ResponseStatusException ex, WebRequest request){
        ExceptionResponse response = new ExceptionResponse(
            ex.getReason(),
            (HttpStatus) ex.getStatusCode(),
            request.getDescription(false)
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        ExceptionResponse response = new ExceptionResponse(
            errorMessage,
            HttpStatus.BAD_REQUEST,
            request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String mensage = "Error violating data integrity";

        ExceptionResponse response = new ExceptionResponse(
            mensage,
            HttpStatus.CONFLICT,
            request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        String errorMessage = ex.getMessage();

        ExceptionResponse response = new ExceptionResponse(
            errorMessage,
            HttpStatus.BAD_REQUEST,
            request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        String errorMessage = "Invalid email or password";

        ExceptionResponse response = new ExceptionResponse(
            errorMessage,
            HttpStatus.UNAUTHORIZED,
            request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ExceptionResponse> handleSignatureException(SignatureException ex, WebRequest request) {
        String errorMessage = "Invalid token signature";

        ExceptionResponse response = new ExceptionResponse(
            errorMessage,
            HttpStatus.UNAUTHORIZED,
            request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionResponse> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        String errorMessage = "Token has expired";

        ExceptionResponse response = new ExceptionResponse(
            errorMessage,
            HttpStatus.UNAUTHORIZED,
            request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler (PermissionDeniedException.class)
    public ResponseEntity<ExceptionResponse> handlePermissionDeniedException(PermissionDeniedException ex, WebRequest request) {
        String errorMessage = "Permission denied";

        ExceptionResponse response = new ExceptionResponse(
            errorMessage,
            HttpStatus.FORBIDDEN,
            request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            ex.getMessage(),
            HttpStatus.NOT_FOUND,
            request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
