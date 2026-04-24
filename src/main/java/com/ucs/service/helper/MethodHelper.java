package com.ucs.service.helper;

import com.ucs.entity.concretes.business.EducationTerm;
import com.ucs.entity.concretes.business.Lesson;
import com.ucs.entity.concretes.user.User;
import com.ucs.entity.enums.RoleType;
import com.ucs.exception.BadRequestException;
import com.ucs.exception.ConflictException;
import com.ucs.exception.ErrorMessageType;
import com.ucs.exception.ResourceNotFoundException;
import com.ucs.payload.request.business.EducationTermRequest;
import com.ucs.payload.response.user.UserResponse;
import com.ucs.repository.business.EducationTermRepository;
import com.ucs.repository.business.LessonRepository;
import com.ucs.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MethodHelper {

    private final UserRepository userRepository;
    private final EducationTermRepository educationTermRepository;
    private final LessonRepository lessonRepository;


    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessageType.USER_NOT_FOUND_WITH_ID, userId));
    }

    public void checkBuildIn(User user) {
        if (Boolean.TRUE.equals(user.getBuiltIn())) {
            throw new BadRequestException(ErrorMessageType.NOT_PERMITTED);
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessageType.USER_NOT_FOUND_WITH_USERNAME, username));
    }

    public void checkAdvisor(User user) {
        if (Boolean.FALSE.equals(user.getIsAdvisor())) {
            throw new BadRequestException(ErrorMessageType.ADVISOR_TEACHER_NOT_FOUND, user.getId());
        }
    }

    public void checkRole(User user, RoleType roleType) {
        if (user.getUserRole() == null || !user.getUserRole().getRoleType().equals(roleType)) {
            throw new ResourceNotFoundException(ErrorMessageType.USER_DOES_NOT_HAVE_ROLE, user.getId(), roleType);
        }
    }

    public String getPath(WebRequest request) {
        return request.getDescription(false);
    }

    public User getUserByUserId(Long userId) {
        return getUserById(userId);
    }

    public long countAllAdmin() {
        return userRepository.countByUserRole_RoleType(RoleType.ADMIN);
    }

    public User getTeacherByUsername(String teacherUsername) {
        User teacher = getUserByUsername(teacherUsername);
        checkRole(teacher, RoleType.TEACHER);
        return teacher;
    }

    public List<User> getStudentById(List<Long> studentsIds) {
        List<User> students = userRepository.findAllByIdIn(studentsIds);

        students.forEach(student -> checkRole(student, RoleType.STUDENT));
        return students;
    }

    public User getAuthenticatedUser(UserResponse authenticatedUser) {
        String username = authenticatedUser.getUsername();
        return getUserByUsername(username);
    }

    public void validateEducationTermDatesForRequest(EducationTermRequest request) {

        if (request.getLastRegistrationDate().isAfter(request.getStartDate())) {
            throw new BadRequestException(ErrorMessageType.EDUCATION_START_DATE_INVALID);
        }

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new BadRequestException(ErrorMessageType.EDUCATION_END_DATE_INVALID);
        }
    }

    public void validateEducationTermDates(EducationTermRequest request) {

        validateEducationTermDatesForRequest(request);

        if (educationTermRepository.existsByTermAndYear(request.getTerm(), request.getStartDate().getYear())) {
            throw new ResourceNotFoundException(ErrorMessageType.EDUCATION_TERM_ALREADY_EXISTS);
        }

        boolean isOverlap = educationTermRepository.findByYear(request.getStartDate().getYear())
                .stream()
                .anyMatch(existingTerm ->
                        existingTerm.getStartDate().equals(request.getStartDate()) ||
                                (existingTerm.getStartDate().isBefore(request.getStartDate()) &&
                                        existingTerm.getEndDate().isAfter(request.getStartDate())) ||
                                (existingTerm.getStartDate().isBefore(request.getEndDate()) &&
                                        existingTerm.getEndDate().isAfter(request.getEndDate())) ||
                                (existingTerm.getStartDate().isAfter(request.getStartDate()) ||
                                        existingTerm.getEndDate().isBefore(request.getEndDate()))
                );
        if (isOverlap) {
            throw new BadRequestException(ErrorMessageType.EDUCATION_DATE_CONFLICT);
        }
    }

    public EducationTerm getEducationTermById(Long id){
        return educationTermRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessageType.EDUCATION_TERM_NOT_FOUND,id));
    }

    public void checkLessonExistenceByName(String lessonName){
        boolean lessonExist=lessonRepository.existsByLessonNameEqualsIgnoreCase(lessonName);

        if(lessonExist){
            throw new ConflictException(ErrorMessageType.LESSON_ALREADY_EXISTS_NAME);
        }
    }

    public Lesson getLessonById(Long id){
        return lessonRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessageType.LESSON_NOT_FOUND_FIELD,id));
    }

}
