package com.ucs.payload.request.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseUserPasswordRequest extends AbstractUserRequest {

    @Size(min = 8, max = 60, message = "Your Password should be at least 8 chars or maximum 60 characters")
    private String password;

    @JsonIgnore
    private Boolean builtIn = false;
}
