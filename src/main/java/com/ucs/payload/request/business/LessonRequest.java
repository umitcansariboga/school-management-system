package com.ucs.payload.request.business;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LessonRequest {

    @NotBlank(message = "Please enter lesson name")
    @Size(min = 2, max = 16, message = "Lesson name should be at least 2 chars")
    private String lessonName;

    @NotNull(message = "Please enter credit score")
    private Integer creditScore;

    @NotNull(message = "Please enter isCompulsory")
    private Boolean isCompulsory;
}
