package com.ucs.payload.request.user;

import com.ucs.payload.request.abstracts.BaseUserRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest extends BaseUserRequest {

    @NotBlank(message = "Please enter mother name")
    @Size(min = 2, max = 16, message = "Your mother name should be at least 2 chars")
    private String motherName;

    @NotBlank(message = "Please enter father name")
    @Size(min = 2, max = 16, message = "Your father name should be at least 2 chars")
    private String fatherName;

    @NotNull(message = "Please select advisor teacher")
    private Long advisorTeacherId;


}
