package com.ucs.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Username must not be empty")
    private String username;

    @NotBlank(message = "Password must be not be empty")
    private String password;
}
