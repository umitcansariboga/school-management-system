package com.ucs.exception;

import lombok.Getter;

@Getter
public enum MessageType {

    // --- 404 NOT FOUND ---
    ROLE_NOT_FOUND("404", "user.role.not.found"),
    USER_NOT_FOUND_ID("404", "user.not.found.id"),
    USER_NOT_FOUND_USERNAME("404", "user.not.found.username"),
    ADVISOR_TEACHER_NOT_FOUND("404", "advisor.teacher.not.found"),
    EDUCATION_TERM_NOT_FOUND("404", "education.term.not.found"),
    LESSON_NOT_FOUND_FIELD("404", "lesson.not.found.field"),
    LESSON_NOT_FOUND_IN_LIST("404", "lesson.not.found.in.list"),
    LESSON_PROGRAM_NOT_FOUND_FIELD("404", "lesson.program.not.found.field"),
    LESSON_PROGRAM_NOT_FOUND("404", "lesson.program.not.found"),
    STUDENT_INFO_NOT_FOUND("404", "student.info.not.found"),
    MEET_NOT_FOUND("404", "meet.not.found"),
    CONTACT_MESSAGE_NOT_FOUND("404", "contact.message.not.found"),

    // --- 409 CONFLICT ---
    USERNAME_ALREADY_REGISTERED("409", "user.username.already.registered"),
    SSN_ALREADY_REGISTERED("409", "user.ssn.already.registered"),
    EMAIL_ALREADY_REGISTERED("409", "user.email.already.registered"),
    PHONE_ALREADY_REGISTERED("409", "user.phone.already.registered"),
    ADVISOR_TEACHER_ALREADY_EXISTS("409", "advisor.teacher.already.exists"),
    EDUCATION_TERM_ALREADY_EXISTS("409", "education.term.exists"),
    LESSON_ALREADY_EXISTS_NAME("409", "lesson.already.exists.name"),
    LESSON_ALREADY_REGISTERED("409", "lesson.already.registered"),
    LESSON_PROGRAM_ALREADY_EXISTS("409", "lesson.program.already.exists"),
    MEET_HOURS_CONFLICT("409", "meet.hours.conflict"),

    // --- 400 BAD REQUEST ---
    PASSWORDS_DO_NOT_MATCH("400", "error.password.mismatch"),
    EDUCATION_START_DATE_INVALID("400", "education.start.date.invalid"),
    EDUCATION_END_DATE_INVALID("400", "education.end.date.invalid"),
    EDUCATION_DATE_CONFLICT("400", "education.date.conflict"),
    TIME_NOT_VALID("400", "error.time.not.valid"),
    USER_ROLE_MISMATCH("400", "user.role.mismatch"),
    WRONG_DATE_FORMAT("400", "contact.error.date.format"),

    // --- 403 FORBIDDEN ---
    NOT_PERMITTED("403", "error.not.permitted"),
    USER_DOES_NOT_HAVE_ROLE("403", "user.no.role");

    private final String code;
    private final String message; // Properties dosyasındaki KEY

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
