package com.ucs.payload.request.updateRequest;

import com.ucs.entity.enums.Gender;
import com.ucs.payload.request.abstracts.AbstractUserRequest;
import jakarta.validation.constraints.*;

public class StudentUpdateByAdminRequest extends AbstractUserRequest {

    @Size(min = 2, max = 30)
    private String name;

    @Size(min = 2, max = 30)
    private String surname;

    @Email(message = "Invalid email")
    private String email;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$")
    private String phoneNumber;

    private Gender gender;

    private String birthPlace;

    @Min(value = 1, message = "Student number must be greater than 0")
    private Integer studentNumber;

    @NotNull
    private Long advisorTeacherId;

    @NotNull
    private Boolean isActive;

    @NotNull
    private Long userRoleId;
}
