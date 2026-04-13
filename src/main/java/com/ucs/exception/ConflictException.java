package com.ucs.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
//@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends BaseException {

    public ConflictException(ErrorMessageType messageType) {
        super(messageType, HttpStatus.CONFLICT);
    }

    public ConflictException(ErrorMessageType messageType, Object... args) {
        super(messageType, HttpStatus.CONFLICT, args);
    }
}
