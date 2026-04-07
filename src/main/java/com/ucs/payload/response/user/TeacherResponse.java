package com.ucs.payload.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.ucs.payload.response.abstracts.BaseUserResponse;
import com.ucs.payload.response.business.LessonProgramResponse;
import com.ucs.payload.views.Views;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class TeacherResponse extends BaseUserResponse {

    @JsonView(Views.Base.class)
    private Set<LessonProgramResponse> lessonPrograms;

    @JsonView(Views.Admin.class)
    private Boolean isAdvisorTeacher;
}
