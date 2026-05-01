package com.ucs.service.user.impl;

import com.ucs.contactmessage.messages.Messages;
import com.ucs.entity.concretes.user.User;
import com.ucs.entity.enums.RoleType;
import com.ucs.exception.BadRequestException;
import com.ucs.exception.ErrorMessageType;
import com.ucs.messages.SuccessMessageType;
import com.ucs.payload.mappers.UserMapper;
import com.ucs.payload.request.UpdatePasswordRequest;
import com.ucs.payload.request.updateRequest.UserBaseUpdateRequest;
import com.ucs.payload.request.updateRequest.UserUpdateByAdminRequest;
import com.ucs.payload.request.updateRequest.UserUpdateByAssistantRequest;
import com.ucs.payload.request.updateRequest.UserUpdateByManagerRequest;
import com.ucs.payload.request.user.UserRequest;
import com.ucs.payload.response.abstracts.BaseUserResponse;
import com.ucs.payload.response.user.UserResponse;
import com.ucs.repository.user.UserRepository;
import com.ucs.service.helper.MethodHelper;
import com.ucs.service.helper.PageableHelper;
import com.ucs.service.helper.UserRoleHelper;
import com.ucs.service.user.IUserService;
import com.ucs.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final PageableHelper pageableHelper;
    private final MethodHelper methodHelper;
    private final UserRoleHelper userRoleHelper;

    @Transactional
    public UserResponse saveUser(UserRequest userRequest, String userRole) {

        uniquePropertyValidator.checkDuplicate(
                userRequest.getUsername(),
                userRequest.getSsn(),
                userRequest.getPhoneNumber(),
                userRequest.getEmail()
        );

        User user = userMapper.requestToUser(userRequest);

        userRoleHelper.assignRole(user, userRole, userRequest.getUsername());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getBuiltIn() == null) {
            user.setBuiltIn(Boolean.FALSE);
        }
        if (user.getIsAdvisor() == null) {
            user.setIsAdvisor(Boolean.FALSE);
        }

        User saveuser = userRepository.save(user);
        return userMapper.userToUserResponse(saveuser);
    }

    public Page<UserResponse> getUsersByPage(int page, int size, String sort, String type, String userRole) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        return userRepository.findByUserRole_RoleName(userRole, pageable)
                .map(userMapper::userToUserResponse);
    }

    public BaseUserResponse getUserById(Long userId) {

        User user = methodHelper.getUserById(userId);

        BaseUserResponse response;

        switch (user.getUserRole().getRoleType()) {
            case STUDENT -> response = userMapper.userToStudentResponse(user);
            case TEACHER -> response = userMapper.userToTeacherResponse(user);
            default -> response = userMapper.userToUserResponse(user);
        }
        return response;
    }


    /* CONTROLLER KATMANINA AKTARILACAK
    * @DeleteMapping("/{id}")
     public ResponseEntity<ResponseMessage<String>> deleteUser(
        @PathVariable Long id, Authentication authentication
        * @AuthenticationPrincipal UserResponse userPrincipal ------alternatif // Direkt cast edilmiş halde gelir!) {

    // Authentication nesnesinden asıl kullanıcı detaylarını (Principal) alıyoruz
    UserResponse userPrincipal = (UserResponse) authentication.getPrincipal();

    return ResponseEntity.ok(userService.deleteUserById(id, userPrincipal));
}*/
    @Transactional
    public String deleteUserById(Long userId, UserResponse authenticatedUser) {
        User targetUser = methodHelper.getUserById(userId);

        methodHelper.checkBuildIn(targetUser);

        RoleType authenticatedRole = RoleType.valueOf(authenticatedUser.getUserRole());
        RoleType targetRole = targetUser.getUserRole().getRoleType();

        if (authenticatedRole == RoleType.ADMIN ||
                (authenticatedRole == RoleType.MANAGER &&
                        (targetRole == RoleType.ASSISTANT_MANAGER ||
                                targetRole == RoleType.TEACHER ||
                                targetRole == RoleType.STUDENT)) ||
                (authenticatedRole == RoleType.ASSISTANT_MANAGER &&
                        (targetRole == RoleType.TEACHER ||
                                targetRole == RoleType.STUDENT))) {

            userRepository.delete(targetUser);

            return Messages.getMessage(SuccessMessageType.USER_DELETED.getMessage());
        }

        throw new BadRequestException(ErrorMessageType.NOT_PERMITTED);
    }

    @Override
    @Transactional
    public BaseUserResponse updateUserByAdmin(UserUpdateByAdminRequest userRequest, Long userId, UserResponse authenticatedUser) {
        User targetUser = methodHelper.getUserById(userId);
        validateUpdatePermission(authenticatedUser, targetUser);

        updateBaseFields(targetUser, userRequest);
        Optional.ofNullable(userRequest.getUsername()).ifPresent(targetUser::setUsername);
        Optional.ofNullable(userRequest.getSsn()).ifPresent(targetUser::setSsn);
        Optional.ofNullable(userRequest.getIsActive()).ifPresent(targetUser::setIsActive);
        Optional.ofNullable(userRequest.getIsAdvisor()).ifPresent(targetUser::setIsAdvisor);

        return userMapper.userToUserResponse(userRepository.save(targetUser));
    }

    @Override
    @Transactional
    public BaseUserResponse updateUserByManager(UserUpdateByManagerRequest userRequest, Long userId, UserResponse authenticatedUser) {
        User targetUser = methodHelper.getUserById(userId);
        validateUpdatePermission(authenticatedUser, targetUser);
        updateBaseFields(targetUser, userRequest);
        Optional.ofNullable(userRequest.getIsActive()).ifPresent(targetUser::setIsActive);
        return userMapper.userToUserResponse(userRepository.save(targetUser));
    }

    @Override
    @Transactional
    public BaseUserResponse updateUserByAssistant(UserUpdateByAssistantRequest userRequest, Long userId, UserResponse authenticatedUser) {
        User targetUser = methodHelper.getUserById(userId);
        validateUpdatePermission(authenticatedUser, targetUser);
        updateBaseFields(targetUser, userRequest);
        return userMapper.userToUserResponse(userRepository.save(targetUser));
    }

    @Transactional
    public void updateBaseFields(User user, UserBaseUpdateRequest userRequest) {
        Optional.ofNullable(userRequest.getName()).ifPresent(user::setName);
        Optional.ofNullable(userRequest.getSurname()).ifPresent(user::setSurname);
        Optional.ofNullable(userRequest.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(userRequest.getPhoneNumber()).ifPresent(user::setPhoneNumber);
        Optional.ofNullable(userRequest.getGender()).ifPresent(user::setGender);
    }

    private void validateUpdatePermission(UserResponse authenticatedUser, User targetUser) {

        RoleType authenticatedRole = RoleType.valueOf(authenticatedUser.getUserRole());

        RoleType targetRole = targetUser.getUserRole().getRoleType();

        if (authenticatedRole == RoleType.ADMIN) {
            return;
        }

        if (authenticatedRole == RoleType.MANAGER &&
                (targetRole == RoleType.ASSISTANT_MANAGER ||
                        targetRole == RoleType.TEACHER ||
                        targetRole == RoleType.STUDENT)) {
            return;
        }

        if (authenticatedRole == RoleType.ASSISTANT_MANAGER &&
                (targetRole == RoleType.TEACHER ||
                        targetRole == RoleType.STUDENT)) {
            return;
        }

        throw new BadRequestException(ErrorMessageType.NOT_PERMITTED);
    }

    @Transactional
    public void updatePassword(UpdatePasswordRequest request, UserResponse authenticatedUser) {
        User user = methodHelper.getUserByUsername(authenticatedUser.getUsername());

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BadRequestException(ErrorMessageType.PASSWORDS_DO_NOT_MATCH);
        }

        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(encodedNewPassword);

        userRepository.save(user);
    }

   /* public BaseUserResponse updateUser(
            UserRequest userRequest, Long userId, UserResponse authenticatedUser) {

        User targetUser = methodHelper.getUserById(userId);

        validateUpdatePermission(authenticatedUser, targetUser);

        uniquePropertyValidator.checkUniqueProperties(targetUser, userRequest);
        User updatedUser = userMapper.updateToUserFromRequest(userRequest, targetUser);

        updatedUser.setBuiltIn(targetUser.getBuiltIn());
        updatedUser.setUserRole(targetUser.getUserRole());
        updatedUser.setIsAdvisor(targetUser.getIsAdvisor());
        updatedUser.setAdvisorTeacherId(targetUser.getAdvisorTeacherId());

        updatedUser.setStudentNumber(targetUser.getStudentNumber());
        updatedUser.setMotherName(targetUser.getMotherName());
        updatedUser.setFatherName(targetUser.getFatherName());
        updatedUser.setIsActive(targetUser.getIsActive());

        updatedUser.setLessonProgramSet(targetUser.getLessonProgramSet());
        updatedUser.setMeetList(targetUser.getMeetList());
        updatedUser.setStudentInfos(targetUser.getStudentInfos());

        if (userRequest.getPassword() != null && !userRequest.getPassword().trim().isEmpty()) {
            updatedUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        } else {
            updatedUser.setPassword(targetUser.getPassword());
        }

        User savedUser = userRepository.save(updatedUser);

        BaseUserResponse response;
        switch (savedUser.getUserRole().getRoleType()) {
            case STUDENT -> response = userMapper.userToStudentResponse(savedUser);
            case TEACHER -> response = userMapper.userToTeacherResponse(savedUser);
            default -> response = userMapper.userToUserResponse(savedUser);
        }
        return response;
    }

    public BaseUserResponse updateUserForUsers(UserRequestWithoutPassword userRequest, UserResponse authenticatedUser) {

        String username = authenticatedUser.getUsername();

        User user = methodHelper.getUserByUsername(username);
        methodHelper.checkBuildIn(user);
        uniquePropertyValidator.checkUniqueProperties(user, userRequest);

        user.setUsername(userRequest.getUsername());
        user.setBirthDay(userRequest.getBirthDay());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setBirthPlace(userRequest.getBirthPlace());
        user.setGender(userRequest.getGender());
        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        userRequest.setSsn(userRequest.getSsn());

        User updatedUser = userRepository.save(user);

        return userMapper.userToUserResponse(updatedUser);
    }

    public List<UserResponse> getUserByName(String name) {
        return userRepository.findByNameContaining(name)
                .stream()
                .map(userMapper::userToUserResponse)
                .toList();
    }*/
}
