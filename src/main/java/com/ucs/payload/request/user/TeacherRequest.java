package com.ucs.payload.request.user;

import com.ucs.payload.request.abstracts.BaseUserRequest;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRequest extends BaseUserRequest {

    @NotNull(message = "Please select lesson")
    private Set<Long> lessonsIdList;

    @NotNull(message = "Please select isAdvisor Teacher ")
    private Boolean isAdvisorTeacher;
}
