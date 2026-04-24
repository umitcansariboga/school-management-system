package com.ucs.payload.request.updateRequest;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateByManagerRequest extends UserBaseUpdateRequest{

    private Boolean isActive;

    @Positive(message = "User role id must be positive")
    private Long userRoleId;

}
