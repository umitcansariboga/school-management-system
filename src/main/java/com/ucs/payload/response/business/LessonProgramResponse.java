package com.ucs.payload.response.business;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.ucs.entity.enums.Day;
import com.ucs.payload.response.user.TeacherResponse;
import com.ucs.payload.views.Views;
import lombok.*;

import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonView(Views.Base.class)
public class LessonProgramResponse {

    private Long lessonProgramId;
    private Day day;
    private LocalTime startTime;
    private LocalTime stopTime;

    private Set<LessonResponse> lessonName;
    private EducationTermResponse educationTerm;
}
