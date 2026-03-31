package com.ucs.payload.request.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ucs.entity.enums.Day;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LessonProgramRequest {

    @NotNull(message = "Please enter day")
    private Day day;

    @NotNull(message = "Please enter start time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull(message = "Please enter stop time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime stopTime;

    @NotNull(message = "Please select lesson")
    @Size(min = 1, message = "Lesson must not be empty")
    private Set<Long> lessonIdList;

    @NotNull(message = "Please enter education term")
    private Long educationTermId;
}
