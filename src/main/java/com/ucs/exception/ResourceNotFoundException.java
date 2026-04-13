package com.ucs.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(ErrorMessageType messageType) {
        super(messageType, HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(ErrorMessageType messageType, Object... args) {
        super(messageType, HttpStatus.NOT_FOUND, args);
    }
}
