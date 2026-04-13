package com.ucs.exception;

import com.ucs.contactmessage.messages.Messages;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
public class BaseException extends RuntimeException {
    private final String errorCode;
    private final HttpStatus httpStatus;
    private final ErrorMessageType messageType;

    public BaseException(ErrorMessageType messageType, HttpStatus httpStatus) {
        super(Messages.getMessage(messageType.getMessage()));
        this.errorCode = messageType.getCode();
        this.httpStatus=httpStatus;
        this.messageType=messageType;
        logError();
    }

    public BaseException(ErrorMessageType messageType, HttpStatus httpStatus, Object... args) {
        super(Messages.getMessage(messageType.getMessage(), args));
        this.errorCode = messageType.getCode();
        this.httpStatus=httpStatus;
        this.messageType=messageType;
        logError();
    }

    private void logError() {
        log.error("[{}] occurred: {} - Code: {}",
                this.getClass().getSimpleName(),
                this.getMessage(),
                this.errorCode);
    }
}
