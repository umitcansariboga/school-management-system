package com.ucs.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends BaseException {

    public BadRequestException(ErrorMessageType messageType) {
        super(messageType, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(ErrorMessageType messageType, Object... args) {
        super(messageType, HttpStatus.BAD_REQUEST, args);
    }
}
