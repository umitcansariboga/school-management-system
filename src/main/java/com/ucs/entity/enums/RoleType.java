package com.ucs.entity.enums;

import com.ucs.exception.ErrorMessageType;
import com.ucs.exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    ADMIN("Admin"),
    TEACHER("Teacher"),
    STUDENT("Student"),
    MANAGER("Dean"),
    ASSISTANT_MANAGER("ViceDean");

    private final String name;

    public static RoleType of(String roleName) {
        for (RoleType role : RoleType.values()) {
            if (role.name().equalsIgnoreCase(roleName)) {
                return role;
            }
        }
        throw new ResourceNotFoundException(ErrorMessageType.ROLE_NOT_FOUND);
    }
}
