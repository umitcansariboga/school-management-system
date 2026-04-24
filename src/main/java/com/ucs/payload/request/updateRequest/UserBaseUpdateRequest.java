package com.ucs.payload.request.updateRequest;

import com.ucs.entity.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBaseUpdateRequest {

    @Size(min = 2, max = 30)
    private String name;

    @Size(min = 2, max = 30)
    private String surname;

    @Email
    private String email;

    @Pattern(
            regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please enter valid phone number"
    )
    private String phoneNumber;

    private Gender gender;
}
