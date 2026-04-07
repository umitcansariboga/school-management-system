package com.ucs.messages;

import lombok.Getter;

@Getter
public enum SuccessMessageType {

    // --- Auth ---
    PASSWORD_CHANGED("200", "success.password.changed"),

    // --- User & Profile ---
    USER_SAVED("201", "success.user.saved"),
    USER_FOUND("200", "success.user.found"),
    USER_DELETED("200", "success.user.deleted"),
    USER_UPDATED("200", "success.user.updated"),
    USER_PROFILE_UPDATED("200", "success.user.profile.updated"),

    // --- Teacher & Student ---
    TEACHER_SAVED("201", "success.teacher.saved"),
    TEACHER_UPDATED("200", "success.teacher.updated"),
    ADVISOR_TEACHER_SAVED("201", "success.advisor.teacher.saved"),
    ADVISOR_TEACHER_DELETED("200", "success.advisor.teacher.deleted"),
    STUDENT_SAVED("201", "success.student.saved"),
    STUDENT_UPDATED("200", "success.student.updated"),

    // --- Education & Lesson ---
    EDUCATION_TERM_SAVED("201", "success.education.term.saved"),
    EDUCATION_TERM_UPDATED("200", "success.education.term.updated"),
    EDUCATION_TERM_DELETED("200", "success.education.term.deleted"),
    LESSON_SAVED("201", "success.lesson.saved"),
    LESSON_FOUND("200", "success.lesson.found"),
    LESSON_DELETED("200", "success.lesson.deleted"),

    // --- Lesson Program ---
    LESSON_PROGRAM_SAVED("201", "success.lesson.program.saved"),
    LESSON_PROGRAM_ADDED("200", "success.lesson.program.added"),
    LESSON_PROGRAM_DELETED("200", "success.lesson.program.deleted"),

    // --- Student Info & Meet ---
    STUDENT_INFO_SAVED("201", "success.student.info.saved"),
    STUDENT_INFO_UPDATED("200", "success.student.info.updated"),
    STUDENT_INFO_DELETED("200", "success.student.info.deleted"),
    MEET_SAVED("201", "success.meet.saved"),
    MEET_UPDATED("200", "success.meet.updated"),
    MEET_FOUND("200", "success.meet.found"),
    MEET_DELETED("200", "success.meet.deleted");

    private final String code;
    private final String message; // Properties dosyasındaki KEY

    SuccessMessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
