package com.ucs.payload.request.updateRequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class StudentUpdateRequest {

    @Size(min = 2, max = 16, message = "Your name should be at least 2 chars")
    private String name;

    @Size(min = 2, max = 16, message = "Your surname should be at least 2 chars")
    private String surname;

    @Pattern(
            regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please enter valid phone number"
    )
    private String phoneNumber;

    @Email(message = "Please enter valid email")
    @Size(max = 50, message = "Your email should be max 50 chars")
    private String email;

    @Size(min = 2, max = 16, message = "Your birthplace should be at least 2 chars")
    private String birthPlace;
}
