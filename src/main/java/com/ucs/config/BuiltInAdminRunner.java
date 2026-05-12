package com.ucs.config;

import com.ucs.entity.concretes.user.UserRole;
import com.ucs.entity.enums.Gender;
import com.ucs.entity.enums.RoleType;
import com.ucs.payload.request.user.UserRequest;
import com.ucs.repository.user.UserRepository;
import com.ucs.repository.user.UserRoleRepository;
import com.ucs.service.helper.MethodHelper;
import com.ucs.service.user.impl.UserRoleService;
import com.ucs.service.user.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class BuiltInAdminRunner implements ApplicationRunner {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userService;
    private final UserRoleRepository userRoleRepository;
    private final MethodHelper methodHelper;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (userRoleService.getAllUserRoles().isEmpty()) {
            UserRole admin = new UserRole();
            admin.setRoleType(RoleType.ADMIN);
            admin.setRoleName("Admin");
            userRoleRepository.save(admin);


            UserRole dean = new UserRole();
            dean.setRoleType(RoleType.MANAGER);
            dean.setRoleName("Dean");
            userRoleRepository.save(dean);

            UserRole viceDean = new UserRole();
            viceDean.setRoleType(RoleType.ASSISTANT_MANAGER);
            viceDean.setRoleName("ViceDean");
            userRoleRepository.save(viceDean);

            UserRole student = new UserRole();
            student.setRoleType(RoleType.STUDENT);
            student.setRoleName("Student");
            userRoleRepository.save(student);

            UserRole teacher = new UserRole();
            teacher.setRoleType(RoleType.TEACHER);
            teacher.setRoleName("Teacher");
            userRoleRepository.save(teacher);

            System.out.println("System roles successfully added to the database");
        }

        if (methodHelper.countAllAdmin() == 0) {

            UserRequest adminRequest = UserRequest.builder()
                    .username("Admin")
                    .name("Uras")
                    .surname("Can")
                    .email("ucs@gmail.com")
                    .ssn("111-11-1111")
                    .password("12345678")
                    .phoneNumber("111-111-1111")
                    .gender(Gender.MALE)
                    .birthDay(LocalDate.of(2024, 9, 16))
                    .birthPlace("Berlin")
                    .build();
            userService.saveUser(adminRequest, "Admin");

            System.out.println("Default Admin account created..");
        }
    }
}
