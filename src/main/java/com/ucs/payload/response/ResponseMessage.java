package com.ucs.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ucs.contactmessage.messages.Messages;
import com.ucs.exception.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage<E> {

    private E object;
    private String message;
    //private HttpStatus httpStatus;
    private LocalDateTime timeStamp;
    private boolean success;
    private String errorCode;
    private String path;

    public static <E> ResponseMessage<E> success(E object, MessageType messageType, WebRequest request,Object... args) {
        return ResponseMessage.<E>builder()
                .success(true)
                .message(Messages.getMessage(messageType.getMessage(),args))
                .object(object)
                .path(request.getDescription(false))
                .timeStamp(LocalDateTime.now())
                .build();
    }

    public static <E> ResponseMessage<E> success(E object, MessageType messageType, Object... args) {
        return ResponseMessage.<E>builder()
                .success(true)
                .message(Messages.getMessage(messageType.getMessage(),args))
                .object(object)
                .timeStamp(LocalDateTime.now())
                .build();
    }

    public static <E> ResponseMessage<E> error(MessageType messageType, String errorCode, WebRequest request,Object... args) {
        return ResponseMessage.<E>builder()
                .success(false)
                .message(Messages.getMessage(messageType.getMessage(),args))
                .errorCode(errorCode)
                .timeStamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();
    }

    public static <E> ResponseMessage<E> error(String message, String errorCode, WebRequest request) {
        return ResponseMessage.<E>builder()
                .success(false)
                .message(message)
                .errorCode(errorCode)
                .timeStamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();
    }
}
