package com.ucs.repository.userRepository;

import com.ucs.entity.concretes.user.User;
import com.ucs.entity.enums.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Double> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsBySnn(String ssn);

    boolean existsByPhoneNumber(String phone);

    boolean existsByEmail(String email);

    Page<User> findByUserRole_RoleName(String roleName, Pageable pageable);

    List<User> findByNameContaining(String name);

    long countByUserRole_RoleType(RoleType roleType);

    List<User> findByAdvisorTeacherId(Long id);

    List<User> findByIsAdvisor(Boolean isAdvisor);

    @Query("SELECT COALESCE(max(u.studentNumber),0) FROM User u")
    int getMaxStudentNumber();

    boolean existsByUserRole_RoleType(RoleType roleType);

    List<User> findAllByInIn(Long[] studentIds);
}
