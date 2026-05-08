package com.ucs.payload.response.abstracts;

import com.fasterxml.jackson.annotation.JsonView;
import com.ucs.entity.enums.Gender;
import com.ucs.payload.views.Views;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseUserResponse {

    private Long id;

    @JsonView(Views.Admin.class)
    private String username;

    @JsonView(Views.Base.class)
    private String name;

    @JsonView(Views.Base.class)
    private String surname;

    @JsonView(Views.Base.class)
    private LocalDate birthDay;

    @JsonView(Views.Admin.class)
    private String ssn;

    @JsonView(Views.Base.class)
    private String birthPlace;

    @JsonView(Views.Base.class)
    private String phoneNumber;

    @JsonView(Views.Base.class)
    private Gender gender;

    @JsonView(Views.User.class)
    private String email;

    @JsonView(Views.Base.class)
    private String userRole;
}
