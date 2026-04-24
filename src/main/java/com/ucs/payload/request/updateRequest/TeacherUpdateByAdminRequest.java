package com.ucs.payload.request.updateRequest;

import com.ucs.entity.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherUpdateByAdminRequest {

    @Size(min = 2, max = 16, message = "Name must be between 2 and 16 characters")
    private String name;

    @Size(min = 2, max = 16, message = "Surname must be between 2 and 16 characters")
    private String surname;

    @Email(message = "Invalid email format")
    @Size(max = 50, message = "Email must be max 50 characters")
    private String email;

    @Pattern(
            regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Invalid phone number format"
    )
    private String phoneNumber;

    @Size(min = 2, max = 16, message = "Birth place must be between 2 and 16 characters")
    private String birthPlace;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDay;

    private Gender gender;

    private Boolean isActive;

    private Boolean isAdvisor;

    @Positive(message = "User role id must be positive")
    private Long userRoleId;

    @Positive(message = "Advisor teacher id must be positive")
    private Long advisorTeacherId;
}
