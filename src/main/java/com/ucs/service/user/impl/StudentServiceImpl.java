package com.ucs.service.user.impl;

import com.ucs.entity.concretes.business.LessonProgram;
import com.ucs.entity.concretes.user.User;
import com.ucs.entity.enums.RoleType;
import com.ucs.messages.SuccessMessageType;
import com.ucs.payload.mappers.UserMapper;
import com.ucs.payload.request.business.ChooseLessonProgramWithId;
import com.ucs.payload.request.updateRequest.StudentUpdateByAdminRequest;
import com.ucs.payload.request.updateRequest.StudentUpdateRequest;
import com.ucs.payload.request.user.StudentRequest;
import com.ucs.payload.response.user.StudentResponse;
import com.ucs.payload.response.user.UserResponse;
import com.ucs.repository.user.UserRepository;
import com.ucs.service.business.ILessonProgramService;
import com.ucs.service.helper.MethodHelper;
import com.ucs.service.user.IStudentService;
import com.ucs.service.validator.DateTimeValidator;
import com.ucs.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements IStudentService {

    private final UserRepository userRepository;
    private final MethodHelper methodHelper;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final ILessonProgramService lessonProgramService;
    private final DateTimeValidator dateTimeValidator;

    @Transactional
    public StudentResponse saveStudent(StudentRequest studentRequest, UserResponse authenticatedUser) {
        User advisorTeacher = methodHelper.getUserById(studentRequest.getAdvisorTeacherId());
        methodHelper.checkAdvisor(advisorTeacher);

        uniquePropertyValidator.checkDuplicate(
                studentRequest.getUsername(),
                studentRequest.getSsn(),
                studentRequest.getPhoneNumber(),
                studentRequest.getEmail()
        );

        User student = userMapper.studentRequestToUser(studentRequest);
        student.setAdvisorTeacherId(advisorTeacher.getId());
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));
        student.setIsActive(true);
        student.setIsAdvisor(Boolean.FALSE);
        student.setStudentNumber(methodHelper.getLastStudentNumber());

        User savedStudent = userRepository.save(student);

        log.info("Student with ID: {} was successfully updated by Manager: {}", savedStudent.getId(), authenticatedUser.getUsername());

        return userMapper.userToStudentResponse(savedStudent);
    }

    @Transactional
    public String changeStatusOfStudent(Long studentId, Boolean status) {

        User user = methodHelper.getUserById(studentId);
        methodHelper.checkRole(user, RoleType.STUDENT);
        user.setIsActive(status);

        userRepository.save(user);

        return "Student is " + (status ? "active" : "passive");
    }

    @Transactional
    public String updateStudent(StudentUpdateRequest studentRequest, UserResponse authenticatedUser) {

        String username = authenticatedUser.getUsername();
        User student = methodHelper.getUserByUsername(username);
        uniquePropertyValidator.checkUniqueProperties(student, studentRequest);

        userMapper.updateToStudentUserFromRequest(studentRequest, student);

        userRepository.save(student);

        return SuccessMessageType.STUDENT_UPDATED.getMessage();
    }

    @Transactional
    public StudentResponse updateStudentForManager(Long userId,
                                                   StudentUpdateByAdminRequest studentRequest,
                                                   UserResponse authenticatedUser) {
        User user = methodHelper.getUserById(userId);
        methodHelper.checkRole(user, RoleType.STUDENT);

        uniquePropertyValidator.checkUniqueProperties(user, studentRequest);

        userMapper.updateToStudentUserByAdminRequest(studentRequest, user);
        User savedStudent = userRepository.save(user);

        log.info("Student with ID: {} was updated by Admin: {}", userId, authenticatedUser.getUsername());

        return userMapper.userToStudentResponse(savedStudent);
    }

    @Transactional
    public StudentResponse addLessonProgramToStudent(ChooseLessonProgramWithId chooseLessonProgramWithId, String username) {
        User student = methodHelper.getUserByUsername(username);
        Set<LessonProgram> lessonProgramSet = lessonProgramService.getLessonProgramByIdSet(chooseLessonProgramWithId.getLessonProgramId());
        Set<LessonProgram> studentCurrentLessonProgram = student.getLessonProgramSet();

        dateTimeValidator.checkLessonPrograms(studentCurrentLessonProgram, lessonProgramSet);
        studentCurrentLessonProgram.addAll(lessonProgramSet);
        student.setLessonProgramSet(studentCurrentLessonProgram);

        User savedStudent = userRepository.save(student);

        return userMapper.userToStudentResponse(savedStudent);
    }
}
