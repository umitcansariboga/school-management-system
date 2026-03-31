package com.ucs.payload.request.user;

import com.ucs.payload.request.abstracts.AbstractUserRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class StudentRequestWithoutPassword extends AbstractUserRequest {

    @NotBlank(message = "Please enter your mother name")
    @Size(min = 2, max = 16, message = "Your mother name should be at least 2 chars")
    private String motherName;

    @NotBlank(message = "Please enter father name")
    @Size(min = 2, max = 16, message = "Your father name should be at least 2 chars")
    private String fatherName;
}
