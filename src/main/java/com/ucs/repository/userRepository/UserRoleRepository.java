package com.ucs.repository.userRepository;

import com.ucs.entity.concretes.user.UserRole;
import com.ucs.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {
    Optional<UserRole> findByRoleType(RoleType roleType);
}
