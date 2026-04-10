package com.ucs.service.validator;

import com.ucs.entity.concretes.business.LessonProgram;
import com.ucs.exception.BadRequestException;
import com.ucs.exception.MessageType;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Set;

@Component
public class DateTimeValidator {

    public void checkTimeWithException(LocalTime start, LocalTime stop) {
        if (!start.isBefore(stop)) {
            throw new BadRequestException(MessageType.TIME_NOT_VALID);
        }
    }

    private Boolean isOverLapping(LessonProgram lp1, LessonProgram lp2) {

        if (!lp1.getDay().equals(lp2.getDay())) {
            return false;
        }

        return lp1.getStartTime().isBefore(lp2.getStopTime()) &&
                lp1.getStopTime().isAfter(lp2.getStartTime());
    }

    private void checkOverlapsWithinRequest(Set<LessonProgram> programs) {
        LessonProgram[] array = programs.toArray(new LessonProgram[0]);

        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if ((isOverLapping(array[i], array[j]))) {
                    throw new BadRequestException(MessageType.LESSON_PROGRAM_ALREADY_EXISTS);
                }
            }
        }
    }

    private void checkOverlapsWithExisting(Set<LessonProgram> existingPrograms, Set<LessonProgram> requestPrograms) {
        boolean hasOverlap = requestPrograms.stream()
                .anyMatch(request -> existingPrograms.stream()
                        .anyMatch(existing -> isOverLapping(existing, request)));

        if (hasOverlap) {
            throw new BadRequestException(MessageType.LESSON_PROGRAM_ALREADY_EXISTS);
        }
    }

    public void checkLessonProgram(Set<LessonProgram> existingPrograms, Set<LessonProgram> requestedPrograms) {

        if (requestedPrograms == null || requestedPrograms.isEmpty()) {
            return;
        }

        if (requestedPrograms.size() > 1) {
            checkOverlapsWithinRequest(requestedPrograms);
        }

        if (existingPrograms != null && !existingPrograms.isEmpty()) {
            checkOverlapsWithExisting(existingPrograms, requestedPrograms);
        }
    }
}
