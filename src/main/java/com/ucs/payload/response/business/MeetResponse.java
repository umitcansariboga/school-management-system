package com.ucs.payload.response.business;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.ucs.payload.views.Views;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetResponse {

    @JsonView(Views.Base.class)
    private Long id;

    @JsonView(Views.Base.class)
    private String description;

    @JsonView(Views.Base.class)
    private LocalDate date;

    @JsonView(Views.Base.class)
    private LocalTime startTime;

    @JsonView(Views.Base.class)
    private LocalTime stopTime;

    @JsonView(Views.Base.class)
    private List<Long> studentsIds;

    @JsonView(Views.Base.class)
    private String teacherName;

    @JsonView(Views.Admin.class)
    private String ssn;

    @JsonView(Views.User.class)
    private String username;

    @JsonView(Views.Admin.class)
    private Long advisorTeacherId;
}
