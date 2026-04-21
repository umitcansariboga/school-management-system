package com.ucs.service.helper;

import com.ucs.entity.concretes.user.User;
import com.ucs.entity.enums.RoleType;
import com.ucs.exception.BadRequestException;
import com.ucs.exception.ErrorMessageType;
import com.ucs.repository.user.UserRepository;
import com.ucs.service.user.impl.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRoleHelper {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;

    public void assignRole(User user, String roleName, String username) {
        // 1. Gelen metni Enum'a çeviriyoruz
        RoleType roleType = RoleType.of(roleName);

        // 2. Kısıtlamaları (Tek Admin/Manager kuralı) kontrol et
        validateUniqueRoles(roleType);

        // 3. Özel durumları işle (Built-in ve Student Number)
        handleSpecialProperties(user, roleType, username);

        // 4. Veritabanından UserRole nesnesini al ve atama yap
        user.setUserRole(userRoleService.getUserRole(roleType));
    }

    private void validateUniqueRoles(RoleType roleType) {
        if (roleType == RoleType.ADMIN || roleType == RoleType.MANAGER) {
            if (userRepository.existsByUserRole_RoleType(roleType)) {
                throw new BadRequestException(
                        roleType == RoleType.ADMIN ? ErrorMessageType.ONLY_ONE_ADMIN : ErrorMessageType.ONLY_ONE_MANAGER
                );
            }
        }
    }

    private void handleSpecialProperties(User user, RoleType roleType, String username) {
        // Admin ve built-in kontrolü
        if (roleType == RoleType.ADMIN && "Admin".equalsIgnoreCase(username)) {
            user.setBuiltIn(true);
        }

        // Öğrenci numarası üretme
        if (roleType == RoleType.STUDENT && user.getStudentNumber() == null) {
            user.setStudentNumber(userRepository.getMaxStudentNumber() + 1);
        }
    }
}
