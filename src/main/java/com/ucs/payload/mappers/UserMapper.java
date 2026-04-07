package com.ucs.payload.mappers;

import com.ucs.entity.concretes.user.User;
import com.ucs.payload.request.abstracts.BaseUserRequest;
import com.ucs.payload.request.user.StudentRequest;
import com.ucs.payload.request.user.TeacherRequest;
import com.ucs.payload.response.user.StudentResponse;
import com.ucs.payload.response.user.TeacherResponse;
import com.ucs.payload.response.user.UserResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toUser(BaseUserRequest baseUserRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userRole", source = "userRole.roleName")
    UserResponse toUserResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    User updateToUserFromRequest(BaseUserRequest baseUserRequest, @MappingTarget User user);

    @Mapping(target = "id", ignore = true)
    User toTeacherUser(TeacherRequest teacherRequest);

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "userRole", source = "userRole.roleName")
    TeacherResponse toTeacherResponse(User teacher);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    User updateToTeacherUserFromRequest(TeacherRequest teacherRequest, @MappingTarget User user);

    @Mapping(target = "id", ignore = true)
    User toStudentUser(StudentRequest studentRequest);

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "userRole", source = "userRole.roleName")
    StudentResponse toStudentResponse(User student);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    User updateToStudentUserFromRequest(StudentRequest studentRequest, @MappingTarget User user);
}
