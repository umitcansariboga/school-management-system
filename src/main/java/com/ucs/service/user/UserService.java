package com.ucs.service.user;

import com.ucs.contactmessage.messages.Messages;
import com.ucs.entity.concretes.user.User;
import com.ucs.entity.enums.RoleType;
import com.ucs.exception.BadRequestException;
import com.ucs.exception.ErrorMessageType;
import com.ucs.exception.ResourceNotFoundException;
import com.ucs.messages.SuccessMessageType;
import com.ucs.payload.mappers.UserMapper;
import com.ucs.payload.request.user.UserRequest;
import com.ucs.payload.response.ResponseMessage;
import com.ucs.payload.response.abstracts.BaseUserResponse;
import com.ucs.payload.response.user.UserResponse;
import com.ucs.repository.user.UserRepository;
import com.ucs.service.helper.MethodHelper;
import com.ucs.service.helper.PageableHelper;
import com.ucs.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final PageableHelper pageableHelper;
    private final MethodHelper methodHelper;

    public ResponseMessage<UserResponse> saveUser(UserRequest userRequest, String userRole) {

        uniquePropertyValidator.checkDuplicate(
                userRequest.getUsername(),
                userRequest.getSsn(),
                userRequest.getPhoneNumber(),
                userRequest.getEmail()
        );

        User user = userMapper.requestToUser(userRequest);
        assignRoleToUser(user, userRole, userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getBuiltIn() == null) {
            user.setBuiltIn(Boolean.FALSE);
        }
        if (user.getIsAdvisor() == null) {
            user.setIsAdvisor(Boolean.FALSE);
        }

        User saveuser = userRepository.save(user);
        return ResponseMessage.success(userMapper.userToUserResponse(saveuser), SuccessMessageType.USER_SAVED);
    }

    private void assignRoleToUser(User user, String userRole, String username) {

        if (userRole.equalsIgnoreCase(RoleType.ADMIN.name()) ||
                userRole.equalsIgnoreCase(RoleType.ADMIN.getName()) {
            if (userRepository.existsByUserRole_RoleType(RoleType.ADMIN)) {
                throw new BadRequestException(ErrorMessageType.ONLY_ONE_ADMIN);
            }
            if (Objects.equals(username, "Admin")) {
                user.setBuiltIn(Boolean.TRUE);
            }

            user.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));
            return;
        }

        if (userRole.equalsIgnoreCase(RoleType.MANAGER.name()) ||
                userRole.equalsIgnoreCase(RoleType.MANAGER.getName())) {
            if (userRepository.existsByUserRole_RoleType(RoleType.MANAGER)) {
                throw new BadRequestException(ErrorMessageType.ONLY_ONE_MANAGER);
            }

            user.setUserRole(userRoleService.getUserRole(RoleType.MANAGER));
            return;
        }

        if (userRole.equalsIgnoreCase(RoleType.ASSISTANT_MANAGER.name()) ||
                userRole.equalsIgnoreCase(RoleType.ASSISTANT_MANAGER.getName())) {

            user.setUserRole(userRoleService.getUserRole(RoleType.ASSISTANT_MANAGER));
            return;
        }

        if (userRole.equalsIgnoreCase(RoleType.TEACHER.name()) ||
                userRole.equalsIgnoreCase(RoleType.TEACHER.getName())) {

            user.setUserRole(userRoleService.getUserRole(RoleType.TEACHER));
            return;
        }

        if (userRole.equalsIgnoreCase(RoleType.STUDENT.name()) ||
                userRole.equalsIgnoreCase(RoleType.STUDENT.getName())) {

            user.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));

            if (user.getStudentNumber() == null) {
                user.setStudentNumber(userRepository.getMaxStudentNumber() + 1);
            }
            return;
        }

        throw new ResourceNotFoundException(ErrorMessageType.ROLE_NOT_FOUND);
    }

    public Page<UserResponse> getUsersByPage(int page, int size, String sort, String type, String userRole) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        return userRepository.findByUserRole_RoleName(userRole, pageable)
                .map(userMapper::userToUserResponse);
    }

    public ResponseMessage<BaseUserResponse> getUserById(Long userId) {

        User user = methodHelper.getUserById(userId);

        BaseUserResponse response;

        switch (user.getUserRole().getRoleType()) {
            case STUDENT -> response = userMapper.userToStudentResponse(user);
            case TEACHER -> response = userMapper.userToTeacherResponse(user);
            default -> response = userMapper.userToUserResponse(user);
        }
        return ResponseMessage.success(response, SuccessMessageType.USER_FOUND);
    }


    /* CONTROLLER KATMANINA AKTARILACAK
    * @DeleteMapping("/{id}")
     public ResponseEntity<ResponseMessage<String>> deleteUser(
        @PathVariable Long id,
        Authentication authentication
        * @AuthenticationPrincipal UserResponse userPrincipal ------alternatif // Direkt cast edilmiş halde gelir!) {

    // Authentication nesnesinden asıl kullanıcı detaylarını (Principal) alıyoruz
    UserResponse userPrincipal = (UserResponse) authentication.getPrincipal();

    return ResponseEntity.ok(userService.deleteUserById(id, userPrincipal));
}*/
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
                (authenticatedRole==RoleType.ASSISTANT_MANAGER &&
                        (targetRole==RoleType.TEACHER ||
                                targetRole==RoleType.STUDENT))) {

            userRepository.delete(targetUser);

            return Messages.getMessage(SuccessMessageType.USER_DELETED.getMessage());
        }

        throw new BadRequestException(ErrorMessageType.NOT_PERMITTED);
    }

    public ResponseMessage<BaseUserResponse> updateUser(
            UserRequest userRequest, Long userId, UserResponse authenticatedUser
    ){

        User targetUser=methodHelper.getUserById(userId);

        validateUpdatePermission(authenticatedUser,targetUser);

        uniquePropertyValidator.checkUniqueProperties(targetUser,userRequest);
        User updatedUser=userMapper.updateToUserFromRequest(userRequest,targetUser);

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

        if(userRequest.getPassword()!=null && !userRequest.getPassword().trim().isEmpty()){
            updatedUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }else {
            updatedUser.setPassword(targetUser.getPassword());
        }

        User savedUser=userRepository.save(updatedUser);

        BaseUserResponse response;
        switch (savedUser.getUserRole().getRoleType()){
            case STUDENT -> response=userMapper.userToStudentResponse(savedUser);
            case TEACHER -> response=userMapper.userToTeacherResponse(savedUser);
            default -> response=userMapper.userToUserResponse(savedUser);
        }
        return ResponseMessage.success(response,SuccessMessageType.SUCCESS_UPDATED);
    }
}
