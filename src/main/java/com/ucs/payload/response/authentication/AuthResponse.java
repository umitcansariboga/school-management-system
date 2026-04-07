package com.ucs.payload.response.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.ucs.payload.views.Views;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {

    @JsonView(Views.Base.class)
    private String username;

    @JsonView(Views.Admin.class)
    private String ssn;

    @JsonView(Views.Base.class)
    private String role;

    @JsonView(Views.User.class)
    private String token;

    @JsonView(Views.Base.class)
    private String name;
}
