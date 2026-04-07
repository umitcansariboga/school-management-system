package com.ucs.payload.request.business;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.Internal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StudentInfoRequest {

    @NotNull(message = "Please select education term")
    private Long educationTermId;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    @NotNull(message = "Please enter midterm exam")
    private Double midtermExam;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    @NotNull(message = "Please enter final exam")
    private Double finalExam;

    @NotNull(message = "Please enter absentee")
    private Integer absentee;

    @NotBlank(message = "Please enter info")
    @Size(min = 10, max = 200, message = "Info should be at least 10 chars")
    private String infoNote;

    @NotNull(message = "Please select lesson")
    private Long lessonId;

    @NotNull(message = "Please select student")
    private Long studentId;
}
