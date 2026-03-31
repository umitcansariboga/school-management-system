package com.ucs.payload.request.business;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ChooseLessonProgramWithId {

    @NotNull(message = "Please select lesson program")
    @Size(min = 1,message = "Lesson must not be empty")
    private Set<Long> lessonProgramId;

}
