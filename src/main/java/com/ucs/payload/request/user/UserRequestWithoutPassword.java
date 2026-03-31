package com.ucs.payload.request.user;

import com.ucs.payload.request.abstracts.AbstractUserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserRequestWithoutPassword extends AbstractUserRequest {
}
