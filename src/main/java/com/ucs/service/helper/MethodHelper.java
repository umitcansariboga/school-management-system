package com.ucs.service.helper;

import com.ucs.entity.concretes.user.User;
import com.ucs.entity.enums.RoleType;
import com.ucs.exception.BadRequestException;
import com.ucs.exception.MessageType;
import com.ucs.exception.ResourceNotFoundException;
import com.ucs.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MethodHelper {

    private final UserRepository userRepository;

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(MessageType.USER_NOT_FOUND_WITH_ID, userId));
    }

    public void checkBuildIn(User user) {
        if (Boolean.TRUE.equals(user.getBuiltIn())) {
            throw new BadRequestException(MessageType.NOT_PERMITTED);
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException(MessageType.USER_NOT_FOUND_WITH_USERNAME, username));
    }

    public void checkAdvisor(User user) {
        if (Boolean.FALSE.equals(user.getIsAdvisor())) {
            throw new BadRequestException(MessageType.ADVISOR_TEACHER_NOT_FOUND, user.getId());
        }
    }

    public void checkRole(User user, RoleType roleType) {
        if (user.getUserRole() == null || !user.getUserRole().getRoleType().equals(roleType)) {
            throw new BadRequestException(MessageType.USER_DOES_NOT_HAVE_ROLE,user.getId(),roleType);
        }
    }
}
