package com.ucs.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Getter
//@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends BaseException {

    public ConflictException(MessageType messageType) {
        super(messageType, HttpStatus.CONFLICT);
    }

    public ConflictException(MessageType messageType, Object... args) {
        super(messageType, HttpStatus.CONFLICT, args);
    }
}
