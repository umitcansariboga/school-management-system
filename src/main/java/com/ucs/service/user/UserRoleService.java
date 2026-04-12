package com.ucs.service.user;

import com.ucs.entity.concretes.user.UserRole;
import com.ucs.entity.enums.RoleType;
import com.ucs.exception.MessageType;
import com.ucs.exception.ResourceNotFoundException;
import com.ucs.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Cacheable(value = "userRole")
    public UserRole getUserRole(RoleType roleType){

        return userRoleRepository.findByRoleType(roleType)
                .orElseThrow(()->
                        new ResourceNotFoundException(MessageType.ROLE_NOT_FOUND));
    }

    @Cacheable(value = "allUserRoles")
    public List<UserRole> getAllUserRoles(){
        return userRoleRepository.findAll();
    }
}
