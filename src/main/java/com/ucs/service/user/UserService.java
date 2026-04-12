package com.ucs.service.user;

import com.ucs.contactmessage.messages.Messages;
import com.ucs.entity.concretes.user.User;
import com.ucs.entity.enums.RoleType;
import com.ucs.exception.BadRequestException;
import com.ucs.exception.MessageType;
import com.ucs.payload.mappers.UserMapper;
import com.ucs.payload.request.user.UserRequest;
import com.ucs.payload.response.ResponseMessage;
import com.ucs.payload.response.user.UserResponse;
import com.ucs.repository.user.UserRepository;
import com.ucs.service.helper.MethodHelper;
import com.ucs.service.helper.PageableHelper;
import com.ucs.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

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

        User user = userMapper.toUser(userRequest);
        assignRoleToUser(user, userRole, userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getBuiltIn() == null) {
            user.setBuiltIn(Boolean.FALSE);
        }
        if (user.getIsAdvisor() == null) {
            user.setIsAdvisor(Boolean.FALSE);
        }

        User saveuser=userRepository.save(user);
        return ResponseMessage.success(userMapper.toUserResponse(user), MessageType.SUCCESS_USER_SAVED);
    }

    private void assignRoleToUser(User user,String userRole,String username){

        if(userRole.equalsIgnoreCase(RoleType.ADMIN.name()) ||
               userRole.equalsIgnoreCase(RoleType.ADMIN.getName()){
            if(userRepository.existsByUserRole_RoleType(RoleType.ADMIN)){
                throw new BadRequestException(MessageType.ONLY_ONE_ADMIN);
            }
            if(Objects.equals(username,"Admin")){
                user.setBuiltIn(Boolean.TRUE);
            }

            user.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));
            return;
        }

        if(userRole.equalsIgnoreCase(RoleType.MANAGER.name()) ||
                userRole.equalsIgnoreCase(RoleType.MANAGER.getName()) ){
            if(userRepository.existsByUserRole_RoleType(RoleType.MANAGER)){
                throw new BadRequestException(MessageType.ONLY_ONE_MANAGER);
            }

            user.setUserRole(userRoleService.getUserRole(RoleType.MANAGER));
            return;
        }

    }


}
