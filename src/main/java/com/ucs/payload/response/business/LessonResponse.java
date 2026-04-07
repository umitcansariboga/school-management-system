package com.ucs.payload.response.business;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.ucs.payload.views.Views;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonView(Views.Base.class)
public class LessonResponse {

    private Long lessonId;

    private String lessonName;

    private Integer creditScore;

    private Boolean isCompulsory;
}
