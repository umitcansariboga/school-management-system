package com.ucs.payload.request.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ucs.entity.enums.Term;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EducationTermRequest {

    @NotNull(message = "Education term must not be empty")
    private Term term;

    @NotNull(message = "Start date must not be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date must not be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "Last Registration date must not be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate lastRegistrationDate;

}
