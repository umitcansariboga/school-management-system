package com.ucs.payload.response.business;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.ucs.entity.enums.Term;
import com.ucs.payload.views.Views;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonView(Views.Base.class)
public class EducationTermResponse {

    private Long id;

    private Term term;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate lastRegistrationDate;
}
