package com.ucs.payload.request.updateRequest;

import com.ucs.payload.request.abstracts.BaseUserPasswordRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TeacherUpdateByAdminRequest extends BaseUserPasswordRequest {

    private Boolean isActive;
    private Boolean isAdvisorTeacher;
    @Positive(message = "User role id must be positive")
    private Long userRoleId;
    @Positive(message = "Advisor teacher id must be positive")
    private Long advisorTeacherId;
    @NotNull(message = "Please select lesson")
    private Set<Long> lessonsIdList;
}
