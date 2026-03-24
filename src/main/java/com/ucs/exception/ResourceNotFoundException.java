package com.ucs.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private final String errorCode;

    public ResourceNotFoundException(MessageType messageType) {
        super(messageType.getMessage());
        this.errorCode = messageType.getCode();
        log.error("Resource Not Found Exception occurend with code: {} and message: {}",
                messageType.getCode(), messageType.getMessage());
    }

    public ResourceNotFoundException(MessageType messageType, Object... args) {
        super(String.format(messageType.getMessage(), args));
        this.errorCode = messageType.getCode();
        log.error("Resource Not Found Exception [Code: {}]: {}",
                this.errorCode, String.format(messageType.getMessage(), args));
    }
}
