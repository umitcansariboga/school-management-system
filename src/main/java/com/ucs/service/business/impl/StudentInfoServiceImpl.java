package com.ucs.service.business.impl;

import com.ucs.entity.concretes.business.EducationTerm;
import com.ucs.entity.concretes.business.Lesson;
import com.ucs.entity.concretes.business.StudentInfo;
import com.ucs.entity.concretes.user.User;
import com.ucs.entity.enums.Note;
import com.ucs.entity.enums.RoleType;
import com.ucs.payload.mappers.StudentInfoMapper;
import com.ucs.payload.request.business.StudentInfoRequest;
import com.ucs.payload.request.business.UpdateStudentInfoRequest;
import com.ucs.payload.response.business.StudentInfoResponse;
import com.ucs.payload.response.user.UserResponse;
import com.ucs.repository.business.StudentInfoRepository;
import com.ucs.service.business.IEducationTermService;
import com.ucs.service.business.ILessonService;
import com.ucs.service.helper.MethodHelper;
import com.ucs.service.helper.PageableHelper;
import com.ucs.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentInfoServiceImpl {

    private final StudentInfoRepository studentInfoRepository;
    private final MethodHelper methodHelper;
    private final IUserService userService;
    private final ILessonService lessonService;
    private final IEducationTermService educationTermService;
    private final StudentInfoMapper studentInfoMapper;
    private final PageableHelper pageableHelper;

    @Value("${midterm.exam.impact.percentage}")
    private Double midtermExamPercentage;

    @Value("${final.exam.impact.percentage}")
    private Double finalExamPercentage;

    @Transactional
    public StudentInfoResponse saveStudentInfo(StudentInfoRequest studentInfoRequest, UserResponse authenticatedUser) {

        String teacherUsername = authenticatedUser.getUsername();

        User teacher = methodHelper.getTeacherByUsername(teacherUsername);
        User student = methodHelper.getUserById(studentInfoRequest.getStudentId());

        methodHelper.checkRole(student, RoleType.STUDENT);

        Lesson lesson = methodHelper.getLessonById(studentInfoRequest.getLessonId());
        EducationTerm educationTerm = methodHelper.getEducationTermById(studentInfoRequest.getEducationTermId());

        methodHelper.checkSameLesson(studentInfoRequest.getStudentId(), lesson.getLessonName());

        Double averageNote = calculateExamAverage(studentInfoRequest.getMidtermExam(),
                studentInfoRequest.getFinalExam());

        Note letterNote = methodHelper.checkLetterGrade(averageNote);

        StudentInfo studentInfo = studentInfoMapper.toStudentInfo(
                studentInfoRequest,
                letterNote,
                averageNote,
                lesson,
                educationTerm
        );

        studentInfo.setStudent(student);
        studentInfo.setTeacher(teacher);

        StudentInfo savedStudentInfo = studentInfoRepository.save(studentInfo);

        return studentInfoMapper.toStudentInfoResponse(savedStudentInfo);
    }

    //helper
    private Double calculateExamAverage(Double midtermExam, Double finalExam) {
        return ((midtermExamPercentage * midtermExam) + (finalExamPercentage * finalExam));
    }

    @Transactional
    public void deleteById(Long studentInfoId) {
        methodHelper.isStudentInfoExistsById(studentInfoId);
        studentInfoRepository.deleteById(studentInfoId);
    }

    @Transactional
    public StudentInfoResponse update(UpdateStudentInfoRequest studentInfoRequest,
                                      Long studentInfoId) {
        StudentInfo studentInfo = methodHelper.isStudentInfoExistsById(studentInfoId);

        Lesson lesson = methodHelper.getLessonById(studentInfoRequest.getLessonId());
        EducationTerm educationTerm = methodHelper.getEducationTermById(studentInfoRequest.getEducationTermId());

        Double noteAverage = calculateExamAverage(studentInfoRequest.getMidTermExam(), studentInfoRequest.getFinalExam());

        Note note = methodHelper.checkLetterGrade(noteAverage);

        studentInfo.setLesson(lesson);
        studentInfo.setEducationTerm(educationTerm);
        studentInfo.setMidtermExam(studentInfoRequest.getMidTermExam());
        studentInfo.setFinalExam(studentInfoRequest.getFinalExam());
        studentInfo.setInfoNote(studentInfoRequest.getInfoNote());
        studentInfo.setExamAverage(noteAverage);
        studentInfo.setLetterGrade(note);

        StudentInfo updatedStudentInfo = studentInfoRepository.save(studentInfo);

        return studentInfoMapper.toStudentInfoResponse(updatedStudentInfo);
    }


}
