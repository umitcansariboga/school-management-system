package com.ucs.service.helper;

import com.ucs.entity.concretes.business.EducationTerm;
import com.ucs.entity.concretes.business.Lesson;
import com.ucs.entity.concretes.business.Meet;
import com.ucs.entity.concretes.business.StudentInfo;
import com.ucs.entity.concretes.user.User;
import com.ucs.entity.enums.Note;
import com.ucs.entity.enums.RoleType;
import com.ucs.exception.BadRequestException;
import com.ucs.exception.ConflictException;
import com.ucs.exception.ErrorMessageType;
import com.ucs.exception.ResourceNotFoundException;
import com.ucs.payload.request.business.EducationTermRequest;
import com.ucs.payload.response.user.UserResponse;
import com.ucs.repository.business.EducationTermRepository;
import com.ucs.repository.business.LessonRepository;
import com.ucs.repository.business.MeetRepository;
import com.ucs.repository.business.StudentInfoRepository;
import com.ucs.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MethodHelper {

    private final UserRepository userRepository;
    private final EducationTermRepository educationTermRepository;
    private final LessonRepository lessonRepository;
    private final StudentInfoRepository studentInfoRepository;
    private final MeetRepository meetRepository;


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

    public EducationTerm getEducationTermById(Long id) {
        return educationTermRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessageType.EDUCATION_TERM_NOT_FOUND, id));
    }

    public void checkLessonExistenceByName(String lessonName) {
        boolean lessonExist = lessonRepository.existsByLessonNameEqualsIgnoreCase(lessonName);

        if (lessonExist) {
            throw new ConflictException(ErrorMessageType.LESSON_ALREADY_EXISTS_NAME);
        }
    }

    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessageType.LESSON_NOT_FOUND_FIELD, id));
    }

    public int getLastStudentNumber() {
        if (!userRepository.existsByUserRole_RoleType(RoleType.STUDENT)) {
            return 1000;
        }

        return userRepository.getMaxStudentNumber() + 1;
    }

    public void checkSameLesson(Long studentId, String lessonName) {
        boolean islessonDuplicateExist = studentInfoRepository.findByStudent_Id(studentId)
                .stream()
                .anyMatch(s -> s.getLesson().getLessonName().equalsIgnoreCase(lessonName));

        if (islessonDuplicateExist) {
            throw new ConflictException(
                    ErrorMessageType.LESSON_ALREADY_EXISTS_NAME, lessonName
            );
        }
    }

    public Note checkLetterGrade(Double average) {
        if (average < 50.00) return Note.FF;
        if (average < 60.00) return Note.DD;
        if (average < 65.00) return Note.CC;
        if (average < 70.00) return Note.CB;
        if (average < 75.00) return Note.BB;
        if (average < 80.00) return Note.BA;
        return Note.AA;
    }

    public StudentInfo isStudentInfoExistsById(Long id) {
        return studentInfoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessageType.STUDENT_INFO_NOT_FOUND, id));
    }

    public Meet isMeetExistById(Long meetId) {
        return meetRepository.findById(meetId).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessageType.MEET_NOT_FOUND, meetId));
    }

    public void isTeacherControl(Meet meet, String username) {
        User teacher = getUserByUsername(username);
        boolean isTeacher = teacher.getUserRole().getRoleType().equals(RoleType.TEACHER);
        boolean isOwner = meet.getAdvisoryTeacher().getId().equals(teacher.getId());

        if (isTeacher && !isOwner) {
            throw new BadRequestException(ErrorMessageType.NOT_PERMITTED);
        }
    }

    public void checkMeetConflict(Long userId, LocalDate date, LocalTime startTime, LocalTime stopTime) {

        User user = getUserByUserId(userId);
        List<Meet> meets;

        if (Boolean.TRUE.equals(user.getIsAdvisor())) {
            meets = meetRepository.findByAdvisoryTeacher_Id(userId);
        } else {
            meets = meetRepository.findByStudentList_Id(userId);
        }

        for (Meet meet : meets) {
            LocalTime existingStartTime = meet.getStartTime();
            LocalTime existingStopTime = meet.getStopTime();

            if (meet.getDate().equals(date)) {

                boolean isConflict =
                        (startTime.isAfter(existingStartTime) && startTime.isBefore(existingStopTime)) ||
                                stopTime.isAfter(existingStartTime) && stopTime.isBefore(existingStopTime) ||
                                startTime.isBefore(existingStartTime) && stopTime.isAfter(existingStopTime) ||
                                startTime.equals(existingStartTime) || stopTime.equals(existingStopTime);

                if (isConflict) {
                    throw new ConflictException(ErrorMessageType.MEET_HOURS_CONFLICT);
                }
            }
        }
    }

}
