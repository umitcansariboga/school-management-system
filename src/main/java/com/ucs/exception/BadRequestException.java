package com.ucs.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Getter
//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends BaseException {

    public BadRequestException(MessageType messageType) {
        super(messageType, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(MessageType messageType, Object... args) {
        super(messageType, HttpStatus.BAD_REQUEST, args);
    }
}
