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

    private Boolean isActive;

    private Boolean isAdvisor;

    @Positive(message = "User role id must be positive")
    private Long userRoleId;
}
