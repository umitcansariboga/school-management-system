package com.ucs.service.user;

import com.ucs.entity.concretes.user.User;
import com.ucs.payload.request.user.UserRequest;
import com.ucs.payload.request.user.UserRequestWithoutPassword;
import com.ucs.payload.response.abstracts.BaseUserResponse;
import com.ucs.payload.response.user.UserResponse;
import org.springframework.data.domain.Page;

public interface IUserService {

    UserResponse saveUser(UserRequest userRequest, String userRole);
    Page<UserResponse> getUsersByPage(int page, int size, String sort, String type, String userRole);
    BaseUserResponse getUserById(Long userId);
    String deleteUserById(Long userId, UserResponse authenticatedUser);
    BaseUserResponse updateUser(UserRequest userRequest, Long userId, UserResponse authenticatedUser);
    BaseUserResponse updateUserForUsers(UserRequestWithoutPassword userRequest, UserResponse authenticatedUser);
}
