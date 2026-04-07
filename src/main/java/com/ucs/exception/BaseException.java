package com.ucs.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class BaseException extends RuntimeException {
    private final String errorCode;

    public BaseException(MessageType messageType) {
        super(messageType.getMessage());
        this.errorCode = messageType.getCode();
        log.error("Business Exception occured: {} - Code: {}", this.getMessage(), this.errorCode);
    }

    public BaseException(MessageType messageType, Object... args) {
        super(String.format(messageType.getMessage(), args));
        this.errorCode = messageType.getCode();
        log.error("Businness Exception occured: {} - Code: {}", this.getMessage(), this.errorCode);
    }
}
