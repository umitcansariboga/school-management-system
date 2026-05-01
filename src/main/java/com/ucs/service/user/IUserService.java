package com.ucs.service.user;

import com.ucs.payload.request.UpdatePasswordRequest;
import com.ucs.payload.request.updateRequest.UserBaseUpdateRequest;
import com.ucs.payload.request.updateRequest.UserUpdateByAdminRequest;
import com.ucs.payload.request.updateRequest.UserUpdateByAssistantRequest;
import com.ucs.payload.request.updateRequest.UserUpdateByManagerRequest;
import com.ucs.payload.request.user.UserRequest;
import com.ucs.payload.response.abstracts.BaseUserResponse;
import com.ucs.payload.response.user.UserResponse;
import org.springframework.data.domain.Page;

public interface IUserService {

    UserResponse saveUser(UserRequest userRequest, String userRole);

    Page<UserResponse> getUsersByPage(int page, int size, String sort, String type, String userRole);

    BaseUserResponse getUserById(Long userId);

    String deleteUserById(Long userId, UserResponse authenticatedUser);
    //BaseUserResponse updateUser(UserRequest userRequest, Long userId, UserResponse authenticatedUser);
    //BaseUserResponse updateUserForUsers(UserRequestWithoutPassword userRequest, UserResponse authenticatedUser);

    //void updateBaseFields(UserBaseUpdateRequest userRequest, Long userId, UserResponse authenticatedUser);
    BaseUserResponse updateUserByAdmin(UserUpdateByAdminRequest userRequest, Long userId, UserResponse authenticatedUser);

    BaseUserResponse updateUserByManager(UserUpdateByManagerRequest userRequest, Long userId, UserResponse authenticatedUser);

    BaseUserResponse updateUserByAssistant(UserUpdateByAssistantRequest userRequest, Long userId, UserResponse authenticatedUser);

    void updatePassword(UpdatePasswordRequest request, UserResponse authenticatedUser);
}