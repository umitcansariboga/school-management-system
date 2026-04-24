package com.ucs.payload.request.updateRequest;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateByAdminRequest extends UserBaseUpdateRequest{

    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    private String username;

    @Pattern(
            regexp = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$",
            message = "Invalid SSN format (e.g. 123-45-6789)"
    )
    private String ssn;

    private Boolean isActive;

    private Boolean isAdvisor;

    @Positive(message = "User role id must be positive")
    private Long userRoleId;
}
