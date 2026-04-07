package com.ucs.contactmessage.messages;

import lombok.Getter;

@Getter
public enum ContactMessageType {

    // --- SUCCESS ---
    SUCCESS_SAVED("201", "contact.message.saved"),
    SUCCESS_UPDATED("200", "contact.message.updated"),
    SUCCESS_DELETED("200", "contact.message.deleted");

    private final String code;
    private final String message;

    ContactMessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
