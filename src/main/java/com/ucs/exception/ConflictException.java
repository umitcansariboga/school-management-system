package com.ucs.exception;

import com.ucs.contactmessage.messages.Messages;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    private final String errorCode;

    public ConflictException(MessageType messageType) {
        super(messageType.getMessage());
        this.errorCode = messageType.getCode();
    }

    public ConflictException(MessageType messageType, Object... args) {
        super(Messages.getMessage(messageType.getMessage(), args));
        this.errorCode = messageType.getCode();
        log.error("Conflict Exception occured: [Code: {}]: {}",
                messageType.getCode(), String.format(messageType.getMessage(), args));
    }
}
