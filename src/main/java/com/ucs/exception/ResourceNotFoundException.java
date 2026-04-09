package com.ucs.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Getter
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(MessageType messageType) {
        super(messageType, HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(MessageType messageType, Object... args) {
        super(messageType, HttpStatus.NOT_FOUND, args);
    }
}
