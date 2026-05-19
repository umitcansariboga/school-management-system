package com.ucs.handler;

import com.ucs.contactmessage.messages.Messages;
import com.ucs.exception.BaseException;
import com.ucs.exception.ErrorMessageType;
import com.ucs.payload.response.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ResponseMessage<Void>> handleBaseException(BaseException exception, WebRequest request) {
        ResponseMessage<Void> response = ResponseMessage.error(
                exception.getMessage(),
                exception.getErrorCode(),
                request
        );

        return ResponseEntity.status(exception.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseMessage<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException exception, WebRequest request) {
        Map<String, String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() != null
                                ? fieldError.getDefaultMessage() : "Invalid value",
                        (existingValue, newValue) -> existingValue + ", " + newValue
                ));

        ResponseMessage<Map<String, String>> response = ResponseMessage.<Map<String, String>>builder()
                .success(false)
                .message("Validation failed for one or more fields")
                .object(errors)
                .errorCode("VALIDATION_ERROR")
                .timeStamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage<Void>> handleAllException(Exception exception, WebRequest request) {
        log.error("Unexpected error occured: ", exception);

        ResponseMessage<Void> response = ResponseMessage.<Void>builder()
                .success(false)
                .message(Messages.getMessage(ErrorMessageType.GLOBAL_EXCEPTION.getMessage()))
                .errorCode(ErrorMessageType.GLOBAL_EXCEPTION.getCode())
                .timeStamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(java.lang.IllegalArgumentException.class)
    public ResponseEntity<ResponseMessage<String>> handleIllegalArgumentException(java.lang.IllegalArgumentException ex, WebRequest request) {

        log.error("Argüman hatası: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseMessage.<String>builder()
                        .message(Messages.getMessage(ErrorMessageType.ROLE_NOT_FOUND.getMessage()))
                        .success(false)
                        .build()
        );
    }

    @ExceptionHandler(org.springframework.security.authorization.AuthorizationDeniedException.class)
    public ResponseEntity<ResponseMessage<Void>> handleAccessDeniedException(AccessDeniedException exception,
                                                                             WebRequest request) {
        ResponseMessage<Void> response = ResponseMessage.<Void>builder()
                .success(false)
                .message(Messages.getMessage(ErrorMessageType.NOT_PERMITTED.getMessage()))
                .errorCode(ErrorMessageType.NOT_PERMITTED.getCode())
                .timeStamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}
