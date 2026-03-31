package com.ucs.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {

    @NotBlank(message = "Please Provide Old Password")
    private String oldPassword;

    @NotBlank(message = "Please Provide New Password")
    @Size(min = 8, max = 60, message = "Your Password should be at least 8 chars or maximum 60 characters")
    private String newPassword;
}
