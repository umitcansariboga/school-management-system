package com.ucs.exception;

import lombok.Getter;

@Getter
public enum MessageType {

    // 404 - Not Found Grubu
    NO_STUDENT_FOUND("404", "student.not.found"),
    NO_BOOK_FOUND("404", "book.not.found"),
    NO_ROLE_FOUND("404", "role.not.found"),
    NO_TEACHER_FOUND("404", "teacher.not.found"),
    NOT_FOUND("404", "contact.message.not.found"),

    // 409 - Conflict Grubu
    CONFLICT_STUDENT_EXCEPTION("409", "student.already.exist"),
    CONFLICT_USER_EXCEPTION("409", "user.already.registered"),
    CONFLICT_BOOK_EXCEPTION("409", "book.already.exist"),
    CONFLICT_EMAIL_EXCEPTION("409", "email.already.exist"),
    CONFLICT_TEACHER_EXCEPTION("409", "teacher.already.exist"),

    // 400 - Bad Request Grubu
    WRONG_DATE_FORMAT("400", "error.date.format"),

    // 500 - Internal Server Error
    GLOBAL_EXCEPTION("500", "error.global"),

    // Başarı Mesajları (Eğer gerekirse)
    SAVED("201", "contact.message.saved"),
    UPDATE("200","contact.message.updated"),
    DELETED("200", "contact.message.deleted");

    private String code;
    private String message; // Bu alan artık mesajın kendisini değil, PROPERTIES'deki KEY'i tutuyor.

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
