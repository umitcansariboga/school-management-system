package com.ucs.payload.request.abstracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ucs.entity.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractUserRequest {

    @NotBlank(message = "Please enter your username")
    @Size(min = 2, max = 30, message = "Your Username should be at least 2 chars")
    private String username;

    @NotBlank(message = "Please enter your name")
    @Size(min = 2, max = 30, message = "Your Name should be at least 2 chars")
    private String name;

    @NotBlank(message = "Please enter your surname")
    @Size(min = 2, max = 30, message = "Your Surname should be at least 2 chars")
    private String surname;

    @NotNull(message = "Please enter your birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "Your birthday can not be in the future")
    private LocalDate birthDay;

    @NotNull
    @Pattern(regexp = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$",
            message = "Please enter valid SSN number")//123-45-666
    private String ssn;

    @NotBlank(message = "Please enter your birthplace")
    @Size(min = 2, max = 16, message = "Your birthplace should be at least 2 chars")
    private String birthPlace;

    @NotNull(message = "Please enter your phone number")
    @Size(min = 2, max = 12, message = "Your phone number should be at least 2 chars")
    @Pattern(regexp = "^((\\\\(\\\\d{3}\\\\))|\\\\d{3})[- .]?\\\\d{3}[- .]?\\\\d{4}$",
            message = "Please enter valid phone number")  //555-444-3322
    private String phoneNumber;

    @NotNull(message = "Please enter your gender")
    private Gender gender;

    @NotBlank(message = "Please enter your email")
    @Email(message = "Please enter valid email")
    @Size(min = 2, max = 12, message = "Your email should be between 5 and 50 chars")
    private String email;
}