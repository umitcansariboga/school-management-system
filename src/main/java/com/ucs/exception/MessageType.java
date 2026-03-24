package com.ucs.exception;

import lombok.Getter;

@Getter
public enum MessageType {

    NO_STUDENT_FOUND("404", "Student not found"),
    NO_BOOK_FOUND("404", "Book not found"),
    NO_ROLE_FOUND("404", "Role not found"),
    CONFLICT_STUDENT_EXCEPTION("409", "The Student %s is already exist"),
    CONFLICT_USER_EXCEPTION("409", "User %s is already registered"),
    CONFLICT_BOOK_EXCEPTION("409", "The Book %s is already exist"),
    GLOBAL_EXCEPTION("500", "An unexpected error occurred"),
    CONFLICT_EMAIL_EXCEPTION("409", "Email %s is already exist"),

    NO_TEACHER_FOUND("404", "Teacher not found"),
    CONFLICT_TEACHER_EXCEPTION("409", "The Teacher %s is already exist");

    private String code;
    private String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
