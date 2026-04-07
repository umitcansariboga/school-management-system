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
public class StudentResponse extends BaseUserResponse {

    @JsonView(Views.User.class)
    private Set<LessonProgramResponse> lessonProgramSet;

    @JsonView(Views.Base.class)
    private Integer studentNumber;

    @JsonView(Views.Admin.class)
    private String motherName;

    @JsonView(Views.Admin.class)
    private String fatherName;

    @JsonView(Views.Admin.class)
    private Boolean isActive;
}
