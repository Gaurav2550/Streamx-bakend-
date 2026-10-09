package com.oid.streamxbackend.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VideoNotFoundException.class)
    public ResponseEntity<ApiError> handleVideoNotFoundException(VideoNotFoundException exception){
        return buildError(HttpStatus.NOT_FOUND , exception.getMessage());
    }

    public ResponseEntity<ApiError> handleVideoAccessDeniedException(VideoAccessDeniedException exception){
        return buildError(HttpStatus.FORBIDDEN , exception.getMessage());
    }


    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyExistsException(
            EmailAlreadyExistsException exception
    ){
        return buildError(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );
    }
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUsernameAlreadyExistsException(
            UsernameAlreadyExistsException exception
    ){
        return buildError(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<ApiError> handleInvalidCredentialException(
            InvalidCredentialException exception
    ){
        return buildError(
                HttpStatus.UNAUTHORIZED,
                exception.getMessage()
        );
    }



    private ResponseEntity<ApiError> buildError(
            HttpStatus status,
            String message
    ){
        ApiError error =  new ApiError(
                status.value(),
                message,
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(
            IllegalArgumentException exception){
        ApiError error =  new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(

            MethodArgumentNotValidException exception
    ){
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Validation faiil");


        ApiError error  = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                message ,
                LocalDateTime.now()
        );
        return  ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);

    }


}
