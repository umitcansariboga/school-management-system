package com.ucs.payload.mappers;

import com.ucs.entity.concretes.user.User;
import com.ucs.payload.request.abstracts.BaseUserRequest;
import com.ucs.payload.request.updateRequest.*;
import com.ucs.payload.request.user.StudentRequest;
import com.ucs.payload.request.user.TeacherRequest;
import com.ucs.payload.response.user.StudentResponse;
import com.ucs.payload.response.user.TeacherResponse;
import com.ucs.payload.response.user.UserResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User requestToUser(BaseUserRequest baseUserRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userRole", source = "userRole.roleName")
    UserResponse userToUserResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    User updateToUserFromRequest(BaseUserRequest baseUserRequest, @MappingTarget User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateBaseFields(BaseUserRequest baseUserRequest, @MappingTarget User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateByAdmin(UserUpdateByAdminRequest adminRequest, @MappingTarget User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User managerRequestToUser(UserUpdateByManagerRequest managerRequest, @MappingTarget User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User assistantRequestToUser(UserUpdateByAssistantRequest assistantRequest, @MappingTarget User user);

    //----------------------------------------------------------
    @Mapping(target = "id", ignore = true)
    User requestToTeacherUser(TeacherRequest teacherRequest);

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "userRole", source = "userRole.roleName")
    TeacherResponse userToTeacherResponse(User teacher);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateToTeacherUserFromRequest(TeacherUpdateRequest teacherUpdateRequest, @MappingTarget User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "ssn", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "builtIn", ignore = true)
    @Mapping(target = "userRole", ignore = true)
    User updateToTeacherUserByAdminRequest(TeacherUpdateByAdminRequest teacherUpdateByAdminRequest, @MappingTarget User user);
    //-------------------------------------------------------------
    @Mapping(target = "id", ignore = true)
    User requestToStudentUser(StudentRequest studentRequest);

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "userRole", source = "userRole.roleName")
    StudentResponse userToStudentResponse(User student);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateToStudentUserFromRequest(StudentUpdateRequest studentUpdateRequest, @MappingTarget User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "ssn", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "builtIn", ignore = true)
    @Mapping(target = "userRole", ignore = true)
    User updateToStudentUserByAdminRequest(StudentUpdateByAdminRequest studentUpdateByAdminRequest, @MappingTarget User user);
}
