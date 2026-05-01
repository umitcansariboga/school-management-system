package com.ucs.service.user.impl;

import com.ucs.entity.concretes.business.LessonProgram;
import com.ucs.entity.concretes.user.User;
import com.ucs.entity.enums.RoleType;
import com.ucs.exception.ConflictException;
import com.ucs.exception.ErrorMessageType;
import com.ucs.payload.mappers.UserMapper;
import com.ucs.payload.request.updateRequest.TeacherUpdateByAdminRequest;
import com.ucs.payload.request.user.TeacherRequest;
import com.ucs.payload.response.user.StudentResponse;
import com.ucs.payload.response.user.TeacherResponse;
import com.ucs.payload.response.user.UserResponse;
import com.ucs.repository.user.UserRepository;
import com.ucs.service.business.ILessonProgramService;
import com.ucs.service.helper.MethodHelper;
import com.ucs.service.helper.PageableHelper;
import com.ucs.service.user.ITeacherService;
import com.ucs.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherServiceImpl implements ITeacherService {
    private final UserRepository userRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final MethodHelper methodHelper;
    private final PageableHelper pageableHelper;
    private final ILessonProgramService lessonProgramService;

    @Transactional
    public TeacherResponse saveTeacher(TeacherRequest teacherRequest) {
        Set<LessonProgram> lessonProgramSet = lessonProgramService
                .getLessonProgramByIdSet(teacherRequest.getLessonsIdList());

        uniquePropertyValidator.checkDuplicate(
                teacherRequest.getUsername(),
                teacherRequest.getSsn(),
                teacherRequest.getEmail(),
                teacherRequest.getPhoneNumber()
        );

        User teacher = userMapper.teacherRequestToUser(teacherRequest);

        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        teacher.setUserRole(userRoleService.getUserRole(RoleType.TEACHER));
        teacher.setIsAdvisor(teacherRequest.getIsAdvisorTeacher());
        teacher.setLessonProgramSet(lessonProgramSet);

        User savedTeacher = userRepository.save(teacher);

        return userMapper.userToTeacherResponse(savedTeacher);
    }

    public List<StudentResponse> getAllStudentByAdvisorUserName(UserResponse authenticatedUser) {
        User teacher = methodHelper.getUserByUsername(authenticatedUser.getUsername());

        methodHelper.checkAdvisor(teacher);

        return userRepository.findByAdvisorTeacherId(teacher.getId())
                .stream()
                .map(userMapper::userToStudentResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public TeacherResponse updateTeacherForManagers(TeacherUpdateByAdminRequest teacherRequest, Long userId, UserResponse authenticatedUser) {

        User user = methodHelper.getUserById(userId);
        methodHelper.checkRole(user, RoleType.TEACHER);
        Set<LessonProgram> lessonPrograms = lessonProgramService.getLessonProgramByIdSet(teacherRequest.getLessonsIdList());

        uniquePropertyValidator.checkUniqueProperties(user, teacherRequest);

        User updatedTeacher = userMapper.updateToTeacherUserByAdminRequest(teacherRequest, user);

        if (teacherRequest.getPassword() != null && !teacherRequest.getPassword().trim().isEmpty()) {
            updatedTeacher.setPassword(passwordEncoder.encode(teacherRequest.getPassword()));
        }
        updatedTeacher.setLessonProgramSet(lessonPrograms);
        updatedTeacher.setUserRole(user.getUserRole());
        updatedTeacher.setIsAdvisor(teacherRequest.getIsAdvisorTeacher());

        User savedUser = userRepository.save(updatedTeacher);

        log.info("Teacher with ID: {} was successfully updated by Manager: {}", user.getId(), authenticatedUser.getUsername());

        return userMapper.userToTeacherResponse(savedUser);
    }

    @Transactional
    public TeacherResponse saveAdvisorTeacher(Long teacherId) {

        User user = methodHelper.getUserById(teacherId);
        methodHelper.checkRole(user, RoleType.TEACHER);
        if (Boolean.TRUE.equals(user.getIsAdvisor())) {
            throw new ConflictException(ErrorMessageType.ADVISOR_TEACHER_ALREADY_EXISTS, teacherId);
        }
        user.setIsAdvisor(Boolean.TRUE);
        userRepository.save(user);

        return userMapper.userToTeacherResponse(user);
    }

    @Transactional
    public UserResponse deleteAdvisorTeacherById(Long teacherId) {
        User user = methodHelper.getUserById(teacherId);
        methodHelper.checkRole(user, RoleType.TEACHER);
        methodHelper.checkAdvisor(user);

        user.setIsAdvisor(Boolean.FALSE);
        userRepository.save(user);

        List<User> allStudents = userRepository.findByAdvisorTeacherId(teacherId);

        if (!allStudents.isEmpty()) {
            allStudents.forEach(student -> student.setAdvisorTeacherId(null));
            userRepository.saveAll(allStudents);
        }
        return userMapper.userToUserResponse(user);
    }

    public Page<UserResponse> getAllAdvisorTeacher(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        return userRepository.findAll(pageable)
                .map(userMapper::userToUserResponse);
    }
}
