package com.ucs.payload.request.updateRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ucs.entity.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherUpdateRequest {
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
    @Size(max = 50, message = "Email max 50 chars")
    private String email;

    @Past(message = "Your birthday can not be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDay;

    @Size(min = 2, max = 16, message = "Your birthplace should be at least 2 chars")
    private String birthPlace;

    private Gender gender;
}
