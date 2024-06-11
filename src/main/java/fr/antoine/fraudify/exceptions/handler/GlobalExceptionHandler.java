package fr.antoine.fraudify.exceptions.handler;

import fr.antoine.fraudify.dto.request.DownloadAudioRequest;
import fr.antoine.fraudify.dto.response.exceptions.ExceptionResponse;
import fr.antoine.fraudify.exceptions.AlreadyExistTrackException;

import fr.antoine.fraudify.exceptions.MetaDataDownloadException;
import fr.antoine.fraudify.exceptions.NotFoundException;
import fr.antoine.fraudify.exceptions.TrackDownloadException;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@RestControllerAdvice

public class GlobalExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ExceptionResponse handleException(
            Exception ex,
            HttpServletRequest request
    ) {
        LOGGER.severe("Internal server error");
        return ExceptionResponse.builder()
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({AlreadyExistTrackException.class})
    public ExceptionResponse handleAlreadyExistTrackException(
            AlreadyExistTrackException ex,
            HttpServletRequest request,
            DownloadAudioRequest downloadAudioRequest
    ) {
        LOGGER.warning("Track already exist : " + downloadAudioRequest.videoUrl());
        return ExceptionResponse.builder()
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {MetaDataDownloadException.class, TrackDownloadException.class})
    public ExceptionResponse handleDownloadRuntimeException(
            MetaDataDownloadException ex,
            HttpServletRequest request
    ) {
        LOGGER.severe(ex.getMessage());
        return ExceptionResponse.builder()
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ExceptionResponse handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        LOGGER.warning("Validation error");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ExceptionResponse.builder()
                .path(request.getRequestURI())
                .message("Validation error")
                .fieldError(errors)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {BadCredentialsException.class})
    public ExceptionResponse handleBadCredentialsException(
            BadCredentialsException ex,
            HttpServletRequest request
    ) {
        LOGGER.warning("Bad credentials");
        return ExceptionResponse.builder()
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {NotFoundException.class})
    public ExceptionResponse handleNotFoundException(
            NotFoundException ex,
            HttpServletRequest request
    ) {
        LOGGER.warning("Not found");
        return ExceptionResponse.builder()
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
