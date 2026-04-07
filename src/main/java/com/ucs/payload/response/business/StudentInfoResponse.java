package com.ucs.payload.response.business;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.ucs.entity.enums.Note;
import com.ucs.entity.enums.Term;
import com.ucs.payload.response.user.StudentResponse;
import com.ucs.payload.views.Views;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentInfoResponse {

    @JsonView(Views.Base.class)
    private Long id;

    @JsonView(Views.User.class)
    private Double midtermExam;

    @JsonView(Views.User.class)
    private Double finalExam;

    @JsonView(Views.Base.class)
    private Integer absentee;

    @JsonView(Views.Admin.class)
    private Double examAverage;

    @JsonView(Views.User.class)
    private String infoNote;

    @JsonView(Views.Base.class)
    private Note letterGrade;

    @JsonView(Views.Base.class)
    private String lessonName;

    @JsonView(Views.Base.class)
    private Integer creditScore;

    @JsonView(Views.Base.class)
    private Boolean isCompulsory;

    @JsonView(Views.Base.class)
    private Term educationTerm;

    @JsonView(Views.User.class)
    private StudentResponse studentResponse;

}
